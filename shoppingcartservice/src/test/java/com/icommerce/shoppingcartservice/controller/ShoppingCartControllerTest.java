package com.icommerce.shoppingcartservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.shoppingcartservice.advice.CustomExceptionHandler;
import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.dto.response.ErrorResponse;
import com.icommerce.shoppingcartservice.exception.DownstreamException;
import com.icommerce.shoppingcartservice.exception.ShoppingCartException;
import com.icommerce.shoppingcartservice.model.ProductCart;
import com.icommerce.shoppingcartservice.model.ShoppingCart;
import com.icommerce.shoppingcartservice.model.ShoppingStatus;
import com.icommerce.shoppingcartservice.service.ShoppingCartService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartControllerTest {

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @Mock
    private ShoppingCartService shoppingCartService;

    private MockMvc mockMvc;

    private ShoppingCart shoppingCart;
    private ProductRequest request;
    private ObjectMapper objectMapper;

    private int shoppingCartErrCode = 20010;
    private String shoppingCartErrMessage = "Shopping cart error";

    private int genericErrCode = 10000;
    private String genericErrMessage = "Internal error";

    private Date now = new Date();

    @Before
    public void setup() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController)
                .setControllerAdvice(customExceptionHandler).build();

        ReflectionTestUtils.setField(customExceptionHandler, "shoppingCartErrCode", shoppingCartErrCode);
        ReflectionTestUtils.setField(customExceptionHandler, "shoppingCartErrMessage", shoppingCartErrMessage);

        ReflectionTestUtils.setField(customExceptionHandler, "genericErrCode", genericErrCode);
        ReflectionTestUtils.setField(customExceptionHandler, "genericErrMessage", genericErrMessage);

        ProductCart productCart = new ProductCart();
        productCart.setId(1);
        productCart.setProductId(1);
        productCart.setQty(2);
        productCart.setPriceOfEach(30000L);
        productCart.setCreatedTime(now);

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1);
        shoppingCart.setUserId(1);
        shoppingCart.setStatus(ShoppingStatus.NEW);
        shoppingCart.setCreatedTime(now);
        shoppingCart.addProduct(productCart);

        when(shoppingCartService.getCurrentShoppingCart(1)).thenReturn(shoppingCart);

        request = new ProductRequest();
        request.setProductId(1);
        request.setQty(2);

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetCurrentShoppingCart() throws Exception {
        mockMvc.perform(get("/api/v1/shopping-cart").header("userId", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(5)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.userId", Matchers.is(1)))
                .andExpect(jsonPath("$.status", Matchers.is("NEW")))
                .andExpect(jsonPath("$.createdTime", Matchers.is(now.getTime())))
                .andExpect(jsonPath("$.products", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.products[0].*", Matchers.hasSize(5)))
                .andExpect(jsonPath("$.products[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.products[0].productId", Matchers.is(1)))
                .andExpect(jsonPath("$.products[0].qty", Matchers.is(2)))
                .andExpect(jsonPath("$.products[0].priceOfEach", Matchers.is(30000)))
                .andExpect(jsonPath("$.products[0].createdTime", Matchers.is(now.getTime())));
    }

    @Test
    public void testAddCartDownstreamError() throws Exception {
        ErrorResponse err = new ErrorResponse();
        err.setErrorCode(10010);
        err.setErrorMessage("Product qty is not enough");

        DownstreamException exception = new DownstreamException(err);

        when(shoppingCartService.addProduct(eq(1), any())).thenThrow(exception);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/shopping-cart")
                .header("userId", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(10010)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is("Product qty is not enough")));
    }

    @Test
    public void testAddCartShoppingCartError() throws Exception {
        when(shoppingCartService.addProduct(eq(1), any())).thenThrow(new ShoppingCartException());

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/shopping-cart")
                .header("userId", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(20010)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is("Shopping cart error")));
    }

    @Test
    public void testAddCartInternalError() throws Exception {
        when(shoppingCartService.addProduct(eq(1), any())).thenThrow(new RuntimeException("Unit test exception"));

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/shopping-cart")
                .header("userId", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(10000)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is("Internal error")));
    }

    @Test
    public void testAddCart() throws Exception {
        when(shoppingCartService.addProduct(eq(1), any())).thenReturn(shoppingCart);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/shopping-cart")
                .header("userId", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(5)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.userId", Matchers.is(1)))
                .andExpect(jsonPath("$.status", Matchers.is("NEW")))
                .andExpect(jsonPath("$.createdTime", Matchers.is(now.getTime())))
                .andExpect(jsonPath("$.products", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.products[0].*", Matchers.hasSize(5)))
                .andExpect(jsonPath("$.products[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.products[0].productId", Matchers.is(1)))
                .andExpect(jsonPath("$.products[0].qty", Matchers.is(2)))
                .andExpect(jsonPath("$.products[0].priceOfEach", Matchers.is(30000)))
                .andExpect(jsonPath("$.products[0].createdTime", Matchers.is(now.getTime())));
    }

}
