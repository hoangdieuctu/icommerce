# Config Server
This is a bridge between [configuration repository](https://github.com/hoangdieuctu/icommerce-config) and the services.
It's allow fetching the config when the starting the services.

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
        │           └── configserver
        │               └── ServerApplication.java
        └── resources
            └── application.yml
```

## Dependencies
```
spring-cloud-config-server
```