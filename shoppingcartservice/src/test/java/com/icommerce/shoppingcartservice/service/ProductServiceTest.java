package com.icommerce.shoppingcartservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.dto.response.ErrorResponse;
import com.icommerce.shoppingcartservice.dto.response.ProductResponse;
import com.icommerce.shoppingcartservice.exception.DownstreamException;
import com.icommerce.shoppingcartservice.exception.ShoppingCartException;
import com.icommerce.shoppingcartservice.service.downstream.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<String> response;

    private ObjectMapper objectMapper;

    private ProductRequest request;

    private String successResponse;
    private String errorResponse;
    private String internalErrorResponse;

    private String productUrl = "http://productservice/api/v1/product/buy";

    @Before
    public void setup() throws JsonProcessingException {
        ReflectionTestUtils.setField(productService, "productUrl", productUrl);

        request = new ProductRequest();
        request.setProductId(1);
        request.setQty(1);

        when(restTemplate.postForEntity(productUrl, request, String.class)).thenReturn(response);

        objectMapper = new ObjectMapper();

        ProductResponse res = new ProductResponse();
        res.setProductId(1);
        res.setQty(1);
        res.setRemainingQty(0);
        res.setPrice(30000L);
        successResponse = objectMapper.writeValueAsString(res);

        ErrorResponse err = new ErrorResponse();
        err.setErrorCode(10011);
        err.setErrorMessage("Product qty is not enough");
        errorResponse = objectMapper.writeValueAsString(err);

        ErrorResponse internalErr = new ErrorResponse();
        internalErr.setErrorCode(10000);
        internalErr.setErrorMessage("Internal error");
        internalErrorResponse = objectMapper.writeValueAsString(internalErr);
    }

    @Test
    public void testBuyProductSuccess() {
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(successResponse);

        ProductResponse productResponse = productService.buyProduct(request);
        Assert.assertEquals(1, productResponse.getProductId());
        Assert.assertEquals(1, productResponse.getQty());
        Assert.assertEquals(0, productResponse.getRemainingQty());
        Assert.assertEquals(30000, productResponse.getPrice(), 0);
    }

    @Test
    public void testBuyProductFail() {
        when(response.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(response.getBody()).thenReturn(errorResponse);

        try {
            productService.buyProduct(request);
            Assert.fail("Need to throw exception");
        } catch (DownstreamException ex) {
            ErrorResponse errorResponse = ex.getErrorResponse();
            Assert.assertEquals(10011, errorResponse.getErrorCode());
            Assert.assertEquals("Product qty is not enough", errorResponse.getErrorMessage());
        }
    }

    @Test(expected = ShoppingCartException.class)
    public void testBuyProductException() {
        when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(response.getBody()).thenReturn(errorResponse);

        productService.buyProduct(request);
    }
}
