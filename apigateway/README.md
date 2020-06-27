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
spring-cloud-starter-zipkin
spring-cloud-starter-gateway
spring-boot-starter-data-jpa
jjwt
```

## Route Configuration
Full configuration: [apigateway-default.yml](https://github.com/hoangdieuctu/icommerce-config/blob/master/apigateway-default.yml)
```yaml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: authenticationservice
          uri: lb://authenticationservice
          predicates:
            - Path=/api/v1/auth/**

        - id: ignorebuyproduct
          uri: no://op
          predicates:
            - Path=/api/v1/product/buy
          filters:
            - SetStatus=400

        - id: productservice
          uri: lb://productservice
          predicates:
            - Path=/api/v1/product/**
          filters:
            - TokenFilter
            - ProductFilter

        - id: shoppingcartservice
          uri: lb://shoppingcartservice
          predicates:
            - Path=/api/v1/shopping-cart/**
          filters:
            - TokenFilter
```