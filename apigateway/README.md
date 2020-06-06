# API Gateway
The gateway that will receive all requests from client, validate jwt token and forward to the services.<br>
This is the only thing that expose to the entire world.
It also store the audit information by using the filters.

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
        │           └── apigateway
        │               ├── ServerApplication.java
        │               ├── exception
        │               │   ├── GatewayException.java
        │               │   └── JwtException.java
        │               ├── filter
        │               │   ├── post
        │               │   │   └── ProductFilter.java
        │               │   └── pre
        │               │       └── TokenFilter.java
        │               ├── model
        │               │   └── ProductAudit.java
        │               └── repository
        │                   └── ProductAuditRepository.java
        └── resources
            ├── application.yml
            └── bootstrap.yml
```

## Dependencies
```
spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-gateway
spring-boot-starter-data-jpa
jjwt
```