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