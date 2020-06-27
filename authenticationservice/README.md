# Authentication Service
Authenticate user and generate token for using with the Api Gateway.

## Supported Login Methods
List out all supported authentication methods.

### Login by Facebook
#### Url
```
/api/v1/auth/login/facebook
```
#### Method
```
GET
```
#### Response
```json
{
    "schema": "Bearer",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNTkxNDAyMjQ1LCJpc3MiOiJodHRwOi8vaWNvbW1lcmNlLmNvbSIsImV4cCI6MTU5MTQwNTg0NX0.1lav2j4QxQi3NE202gfmrF85cNGfLgUyQC5yjsmSIPQ"
}
```

## Work Flow
### Login by Facebook
![picture](login-facebook.png)

## Project Structure
```bash
├── Dockerfile
├── README.md
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── icommerce
    │   │           └── authenticationservice
    │   │               ├── ServerApplication.java
    │   │               ├── constant
    │   │               │   └── Constants.java
    │   │               ├── controller
    │   │               │   └── AuthController.java
    │   │               ├── dto
    │   │               │   └── JwtDto.java
    │   │               ├── model
    │   │               │   └── User.java
    │   │               ├── repository
    │   │               │   └── UserRepository.java
    │   │               └── service
    │   │                   ├── AuthService.java
    │   │                   ├── JwtService.java
    │   │                   ├── UserService.java
    │   │                   └── impl
    │   │                       ├── AuthServiceImpl.java
    │   │                       ├── JwtServiceImpl.java
    │   │                       └── UserServiceImpl.java
    │   └── resources
    │       ├── application.yml
    │       └── bootstrap.yml
    └── test
        └── java
            └── com
                └── icommerce
                    └── authenticationservice
                        ├── controller
                        │   └── AuthControllerTest.java
                        └── service
                            ├── AuthServiceTest.java
                            ├── JwtServiceTest.java
                            └── UserServiceTest.java

```

## Dependencies
```
spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-zipkin
spring-boot-starter-web
spring-boot-starter-data-jpa
jjwt
```