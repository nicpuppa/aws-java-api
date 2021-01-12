package com.plansoft.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plansoft.ApiGatewayResponse;
import com.plansoft.Response;
import com.plansoft.dao.DoctorDAO;
import com.plansoft.entity.Doctor;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;

public class CreateDoctorHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            // get the 'body' from input
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
            DoctorDAO dao = new DoctorDAO();

            logger.info(body.toString());

            // create the Doctor object for post
            Doctor doctor = new Doctor();
            doctor.setName(body.get("name").asText());
            doctor.setSurname(body.get("surname").asText());
            logger.error("SONO QUI");

            dao.insert(doctor);

            // send the response back
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(doctor)
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
                    .build();

        } catch (Exception ex) {
            logger.error("Error in saving doctor: " + ex);

            // send the error response back
            Response responseBody = new Response("Error in saving doctor: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(ex)
                    .setObjectBody(ex)
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
                    .build();
        }
    }

}
