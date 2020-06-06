# Eureka Server
The discovery server that allow all the services connect to.<br>
It's allow to discover a service when the other want to access.

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
        │           └── eurekaserver
        │               └── ServerApplication.java
        └── resources
            ├── application.yml
            └── bootstrap.yml
```

## Dependencies
```
spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-server
```