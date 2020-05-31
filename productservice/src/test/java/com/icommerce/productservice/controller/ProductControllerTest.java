package com.icommerce.productservice.controller;

import com.icommerce.productservice.advice.CustomExceptionHandler;
import com.icommerce.productservice.dto.response.ProductResponse;
import com.icommerce.productservice.exception.ProductNotFoundException;
import com.icommerce.productservice.exception.ProductOutOfQtyException;
import com.icommerce.productservice.model.Product;
import com.icommerce.productservice.service.ProductService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    private int productNotFoundErrorCode = 10010;
    private String productNotFoundErrorMessage = "Product not found";

    private int productOutOfQtyCode = 10011;
    private String productOutOfQtyMessage = "Product qty is not enough";

    private int exceptionCode = 10000;
    private String exceptionMessage = "Internal error";

    @Before
    public void setup() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(customExceptionHandler).build();

        ReflectionTestUtils.setField(customExceptionHandler, "productNotFoundCode", productNotFoundErrorCode);
        ReflectionTestUtils.setField(customExceptionHandler, "productNotFoundMessage", productNotFoundErrorMessage);

        ReflectionTestUtils.setField(customExceptionHandler, "productOutOfQtyCode", productOutOfQtyCode);
        ReflectionTestUtils.setField(customExceptionHandler, "productOutOfQtyMessage", productOutOfQtyMessage);

        ReflectionTestUtils.setField(customExceptionHandler, "exceptionCode", exceptionCode);
        ReflectionTestUtils.setField(customExceptionHandler, "exceptionMessage", exceptionMessage);

        Product product = new Product();
        product.setId(1);
        product.setName("Apple MacBook");
        product.setBranch("Apple");
        product.setColor("White");
        product.setDescription("13 Inch");
        product.setQtyInStock(2);
        product.setBuyPrice(43000000L);

        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productService.search(any())).thenReturn(products);
        when(productService.getProduct(1)).thenReturn(product);
        when(productService.getProduct(2)).thenThrow(new ProductNotFoundException());

        ProductResponse response = new ProductResponse();
        response.setProductId(1);
        response.setQty(3);
        response.setRemainingQty(2);

        when(productService.buyProduct(any())).thenReturn(response);
    }

    @Test
    public void testSearchProduct() throws Exception {
        mockMvc.perform(post("/api/v1/product/search").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].*", Matchers.hasSize(7)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Apple MacBook")))
                .andExpect(jsonPath("$[0].branch", Matchers.is("Apple")))
                .andExpect(jsonPath("$[0].color", Matchers.is("White")))
                .andExpect(jsonPath("$[0].description", Matchers.is("13 Inch")))
                .andExpect(jsonPath("$[0].qtyInStock", Matchers.is(2)))
                .andExpect(jsonPath("$[0].buyPrice", Matchers.is(43000000)));
    }

    @Test
    public void testGetProduct() throws Exception {
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(7)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Apple MacBook")))
                .andExpect(jsonPath("$.branch", Matchers.is("Apple")))
                .andExpect(jsonPath("$.color", Matchers.is("White")))
                .andExpect(jsonPath("$.description", Matchers.is("13 Inch")))
                .andExpect(jsonPath("$.qtyInStock", Matchers.is(2)))
                .andExpect(jsonPath("$.buyPrice", Matchers.is(43000000)));
    }

    @Test
    public void testGetProductNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/product/2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(productNotFoundErrorCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(productNotFoundErrorMessage)));
    }

    @Test
    public void testBuyProductNotFound() throws Exception {
        when(productService.buyProduct(any())).thenThrow(new ProductNotFoundException());

        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(productNotFoundErrorCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(productNotFoundErrorMessage)));
    }

    @Test
    public void testBuyProductOutOfQty() throws Exception {
        when(productService.buyProduct(any())).thenThrow(new ProductOutOfQtyException());

        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(productOutOfQtyCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(productOutOfQtyMessage)));
    }

    @Test
    public void testBuyProduct() throws Exception {
        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.productId", Matchers.is(1)))
                .andExpect(jsonPath("$.qty", Matchers.is(3)))
                .andExpect(jsonPath("$.remainingQty", Matchers.is(2)));
    }

    @Test
    public void testBuyProductInternalServerError() throws Exception {
        when(productService.buyProduct(any())).thenThrow(new RuntimeException("Unit test exception."));

        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(exceptionCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(exceptionMessage)));
    }
}
