# iCommerce

An online shopping application which sell the products. <br>
User can search the products they want then add to a shopping cart and proceed to place an order.<br>
To manage and make the users satisfied, all the users' activities are related to the products such as finding, filtering, viewing detail information will be stored.

### Table of Contents
**[Entity Relationship Diagram](#entity-relationship-diagram)**<br>
**[System Architecture](#system-architecture)**<br>
**[System Workflow](#system-workflow)**<br>
**[Configuration Repository](#configuration-repository)**<br>
**[Service Details](#service-details)**<br>
**[How To Run](#how-to-run)**<br>
**[Generated Data](#generated-data)**<br>
**[CURL Commands](#curl-commands)**<br>

## Entity Relationship Diagram
![picture](erd.png)

## System Architecture
![picture](system-architecture.png)

## System Workflow
![picture](system-workflow.png)

## Configuration Repository
To manage and share the configuration between the micro services, configuration repository is required for the entire system.<br>
Repo: [icommerce-config](https://github.com/hoangdieuctu/icommerce-config)

The repository contains the configuration of all services with '**default**' profile.

**Structure**
```bash
├── apigateway-default.yml
├── application-default.yml
├── authenticationservice-default.yml
├── eurekaserver-default.yml
├── productservice-default.yml
└── shoppingcartservice-default.yml
```

## Service Details
The project description, structure and dependencies will be in each project folder. 

**Service folders**

[apigateway](https://github.com/hoangdieuctu/icommerce/tree/master/apigateway)<br>
[authenticationservice](https://github.com/hoangdieuctu/icommerce/tree/master/authenticationservice)<br>
[configserver](https://github.com/hoangdieuctu/icommerce/tree/master/configserver)<br>
[eurekaserver](https://github.com/hoangdieuctu/icommerce/tree/master/eurekaserver)<br>
[zipkinserver](https://github.com/hoangdieuctu/icommerce/tree/master/zipkinserver)<br>
[productservice](https://github.com/hoangdieuctu/icommerce/tree/master/productservice)<br>
[shoppingcartservice](https://github.com/hoangdieuctu/icommerce/tree/master/shoppingcartservice)<br>
[swaggerservice](https://github.com/hoangdieuctu/icommerce/tree/master/swaggerservice)<br>

## How To Run
Make sure that the machine installed java 8 (at least) and maven.<br>
The services should be started by the order.

#### Run Config Server
```bash
mvn clean install
java -jar target/configserver-*.jar
```

#### Run Eureka Server
```bash
mvn clean install
java -jar target/eurekaserver-*.jar
```

#### Run Zip Server
The place to trace the requests through all services.<br>
Access: http://localhost:9411
```bash
java -jar zipkinserver.jar
```

#### Run API Gateway
```bash
mvn clean install
java -jar target/apigateway-*.jar
```

#### Run Authentication Service
```bash
mvn clean install
java -jar target/authenticationservice-*.jar
```

#### Run Product Service
```bash
mvn clean install
java -jar target/productservice-*.jar
```

#### Run Shopping Cart Service
```bash
mvn clean install
java -jar target/shoppingcartservice-*.jar
``` 

#### Run Swagger Service
The swagger service is a centralized swagger tool for all services.<br>
Access: http://localhost:8085/swagger-ui.html
```bash
mvn clean install
java -jar target/swaggerservice-*.jar
``` 

## Generated Data
For testing purpose, when starting the services, there are some data are generated (based on the configuration) as the table below.

### Products
| id | name | color | branch | description | price | qty |
| -- | -- | -- | -- | -- | -- | -- |
| 1 | MacBook Pro 2013 | White | Apple | Apply MacBook Pro 2013 13 Inch | 43000000 | 20 |
| 2 | MacBook Air 2017 | White | Apple | Apply MacBook Air 2017 13 Inch | 30000000 | 5 |
| 3 | Dell Vostro 3590 | Black | Dell | Core i7 - 8GB RAM | 35000000 | 7 |
| 4 | Acer Predator | Black | Acer | Triton 500 - 8GB RAM | 79900000 | 2 |

## CURL Commands
Before testing, make sure all services are up and registered to Eureka Server correctly.

Access Eureka Server: http://localhost:9090/


### Login by Facebook
Return the jwt token that use for the requests later.
```bash
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/v1/auth/login/facebook
```

### Search products
*Make sure the authorization is replaced by the new token.*
```bash
curl -d '{"branch":"Apple","price":{"from":10000000,"to":90000000},"sort":{"sortBy":"branch","orderBy":"asc"}}' -H "Content-Type: application/json" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNTkxNDI5MjYzLCJpc3MiOiJodHRwOi8vaWNvbW1lcmNlLmNvbSIsImV4cCI6MTU5MTQzMjg2M30.RtuIjc2QgyNw2IzINZn5gcOsG1hPmiLbbnosU7bpFUw" -X POST http://localhost:8080/api/v1/product/search
```

### Get a product
*Make sure the authorization is replaced by the new token.*
```bash
curl -H "Content-Type: application/json" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNTkxNDI5MjYzLCJpc3MiOiJodHRwOi8vaWNvbW1lcmNlLmNvbSIsImV4cCI6MTU5MTQzMjg2M30.RtuIjc2QgyNw2IzINZn5gcOsG1hPmiLbbnosU7bpFUw" -X GET http://localhost:8080/api/v1/product/1
```

### Add a product to the shopping cart
*Make sure the authorization is replaced by the new token.*
```bash
curl -d '{"productId":2,"qty":1}' -H "Content-Type: application/json" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNTkxNDI5MjYzLCJpc3MiOiJodHRwOi8vaWNvbW1lcmNlLmNvbSIsImV4cCI6MTU5MTQzMjg2M30.RtuIjc2QgyNw2IzINZn5gcOsG1hPmiLbbnosU7bpFUw" -X POST http://localhost:8080/api/v1/shopping-cart
```

## Get current shopping cart
*Make sure the authorization is replaced by the new token.*
```bash
curl -H "Content-Type: application/json" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNTkxNDI5MjYzLCJpc3MiOiJodHRwOi8vaWNvbW1lcmNlLmNvbSIsImV4cCI6MTU5MTQzMjg2M30.RtuIjc2QgyNw2IzINZn5gcOsG1hPmiLbbnosU7bpFUw" -X GET http://localhost:8080/api/v1/shopping-cart
```
