# Product Service
The service for manage all the products. Allow searching, filtering and view detail of a product.

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
    │   │           └── productservice
    │   │               ├── ServerApplication.java
    │   │               ├── advice
    │   │               │   └── CustomExceptionHandler.java
    │   │               ├── controller
    │   │               │   └── ProductController.java
    │   │               ├── dto
    │   │               │   ├── request
    │   │               │   │   ├── Order.java
    │   │               │   │   ├── PriceCriteria.java
    │   │               │   │   ├── ProductCriteria.java
    │   │               │   │   ├── ProductRequest.java
    │   │               │   │   └── Sort.java
    │   │               │   └── response
    │   │               │       ├── ErrorResponse.java
    │   │               │       └── ProductResponse.java
    │   │               ├── exception
    │   │               │   ├── ProductException.java
    │   │               │   ├── ProductNotFoundException.java
    │   │               │   └── ProductOutOfQtyException.java
    │   │               ├── generator
    │   │               │   └── ProductGenerator.java
    │   │               ├── model
    │   │               │   └── Product.java
    │   │               ├── repository
    │   │               │   └── ProductRepository.java
    │   │               └── service
    │   │                   ├── ProductService.java
    │   │                   └── impl
    │   │                       └── ProductServiceImpl.java
    │   └── resources
    │       ├── application.yml
    │       └── bootstrap.yml
    └── test
        ├── java
        │   └── com
        │       └── icommerce
        │           └── productservice
        │               ├── controller
        │               │   └── ProductControllerTest.java
        │               └── service
        │                   ├── ProductServiceSpringTest.java
        │                   └── ProductServiceTest.java
        └── resources
            └── application.yml
```

## Dependencies
```
spring-cloud-starter-config
spring-cloud-starter-netflix-eureka-client
spring-boot-starter-web
spring-boot-starter-data-jpa
```

## Generated Data
For testing purpose, when the service starting it will generate some products as table below:

| id | name | color | branch | description | price | qty |
| -- | -- | -- | -- | -- | -- | -- |
| 1 | MacBook Pro 2013 | White | Apple | Apply MacBook Pro 2013 13 Inch | 43000000 | 20 |
| 2 | MacBook Air 2017 | White | Apple | Apply MacBook Air 2017 13 Inch | 30000000 | 5 |
| 3 | Dell Vostro 3590 | Black | Dell | Core i7 - 8GB RAM | 35000000 | 7 |
| 4 | Acer Predator | Black | Acer | Triton 500 - 8GB RAM | 79900000 | 2 |

