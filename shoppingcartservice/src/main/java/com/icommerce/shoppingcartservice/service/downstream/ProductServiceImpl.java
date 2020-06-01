package com.icommerce.shoppingcartservice.service.downstream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.dto.response.ErrorResponse;
import com.icommerce.shoppingcartservice.dto.response.ProductResponse;
import com.icommerce.shoppingcartservice.exception.DownstreamException;
import com.icommerce.shoppingcartservice.exception.ShoppingCartException;
import com.icommerce.shoppingcartservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${microservice.product.buy.url}")
    private String productUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProductResponse buyProduct(ProductRequest request) {
        logger.debug("Call post request: {}", productUrl);

        ResponseEntity<String> response = restTemplate.postForEntity(productUrl, request, String.class);
        HttpStatus status = response.getStatusCode();
        String body = response.getBody();
        logger.debug("Response status: {}", status);
        logger.debug("Response body: {}", body);

        ObjectMapper objectMapper = new ObjectMapper();
        if (status.is2xxSuccessful()) {
            try {
                return objectMapper.readValue(body, ProductResponse.class);
            } catch (JsonProcessingException e) {
                logger.error("Cannot parse json response. ", e);
            }
        }

        if (status.is4xxClientError()) {
            try {
                ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);
                throw new DownstreamException(errorResponse);
            } catch (JsonProcessingException e) {
                logger.error("Cannot parse json response. ", e);
            }
        }

        throw new ShoppingCartException("Could not find product info");
    }
}
