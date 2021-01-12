package com.plansoft.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.plansoft.configuration.DynamoDBAdapter;
import com.plansoft.entity.Doctor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DoctorDAO {

    private static DynamoDBAdapter db_adapter;
    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;

    private Logger logger = Logger.getLogger(this.getClass());

    public DoctorDAO() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .build();

        this.db_adapter = DynamoDBAdapter.getInstance();
        this.client = this.db_adapter.getDbClient();
        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
    }

    public Boolean ifTableExists() {
        return this.client.describeTable("products_table").getTable().getTableStatus().equals("ACTIVE");
    }

    public List<Doctor> findAll() throws IOException {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Doctor> results = this.mapper.scan(Doctor.class, scanExp);
        for (Doctor p : results) {
            logger.info("Products - list(): " + p.toString());
        }
        return results;
    }

    public Doctor findById(String id) throws IOException {
        Doctor doctor = null;

        HashMap<String, AttributeValue> av = new HashMap<>();
        av.put(":v1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<Doctor> queryExp = new DynamoDBQueryExpression<Doctor>()
                .withKeyConditionExpression("id = :v1")
                .withExpressionAttributeValues(av);

        PaginatedQueryList<Doctor> result = this.mapper.query(Doctor.class, queryExp);
        if (result.size() > 0) {
            doctor = result.get(0);
            logger.info("Products - get(): product - " + doctor.toString());
        } else {
            logger.info("Products - get(): product - Not Found.");
        }
        return doctor;
    }

    public void insert(Doctor doctor) throws IOException {
        logger.info("Products - save(): " + doctor.toString());
        this.mapper.save(doctor);
    }

    public Boolean delete(String id) throws IOException {
        Doctor doctor = null;

        // get product if exists
        doctor = findById(id);
        if (doctor != null) {
            logger.info("Products - delete(): " + doctor.toString());
            this.mapper.delete(doctor);
        } else {
            logger.info("Products - delete(): product - does not exist.");
            return false;
        }
        return true;
    }

}
