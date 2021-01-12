# Serverless REST API in Java/Maven using DynamoDB

## Install Pre-requisites:
- `node` and `npm`
- Install JDK from [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- Install [Apache Maven](https://maven.apache.org/)
- Install and configure [AWS CLI](https://docs.aws.amazon.com/it_it/cli/latest/userguide/install-cliv2.html)
- Install Servless framework:
```
npm install -g serverless
```

## Build the Java Project
```
mvn clean install
```

## Deploy the serverless app
```
sls deploy

....
  None
endpoints:
  GET - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/doctors
  GET - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/doctors/{id}
  POST - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/doctors
  DELETE - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/doctors/{id}

......
 ```
 
 ## Removing the services
 ```
 sls remove
 ```
