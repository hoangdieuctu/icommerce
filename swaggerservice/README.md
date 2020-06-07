# Swagger Service
A centralize swagger for all services.

## The problem
The swagger is already implemented for each services.<br>
However, each service will have its own endpoint and to access the Swagger-UI and we have to use a different URL for different services.

## Solution
- Allow CORS of all services.
- Get the list of registered services from the eureka server.
- For each registered service, get the swagger definition and store it locally.
- Interval refresh the definition (every 30 seconds).
- Provide a endpoint to get the swagger definitions by service name.

## Project Structure
```bash
├── Dockerfile
├── README.md
├── pom.xml
└── src
    └── main
        ├── java
        │   └── com
        │       └── icommerce
        │           └── swaggerservice
        │               ├── ServerApplication.java
        │               ├── config
        │               │   └── SwaggerConfig.java
        │               ├── controller
        │               │   └── SwaggerController.java
        │               ├── service
        │               │   └── ServiceDefinitionUpdater.java
        │               └── swagger
        │                   └── ServiceDefinitionHolder.java
        └── resources
            ├── application.yml
            └── bootstrap.yml
```

## Dependencies
```
spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-client
spring-boot-starter-web
```

## Testing
Access: http://localhost:8085