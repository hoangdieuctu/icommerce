package com.icommerce.productservice;

import com.icommerce.productservice.generator.ProductGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@EnableDiscoveryClient
@SpringBootApplication
public class ServerApplication {

    @Autowired
    private ProductGenerator productGenerator;

    @PostConstruct
    public void generateData() {
        productGenerator.generateProducts();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
