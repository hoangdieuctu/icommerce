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
import java.util.Date;
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

    private int productNotFoundErrCode = 10010;
    private String productNotFoundErrMessage = "Product not found";

    private int productOutOfQtyErrCode = 10011;
    private String productOutOfQtyErrMessage = "Product qty is not enough";

    private int genericErrCode = 10000;
    private String genericErrMessage = "Internal error";

    private Date time = new Date();

    @Before
    public void setup() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(customExceptionHandler).build();

        ReflectionTestUtils.setField(customExceptionHandler, "productNotFoundErrCode", productNotFoundErrCode);
        ReflectionTestUtils.setField(customExceptionHandler, "productNotFoundErrMessage", productNotFoundErrMessage);

        ReflectionTestUtils.setField(customExceptionHandler, "productOutOfQtyErrCode", productOutOfQtyErrCode);
        ReflectionTestUtils.setField(customExceptionHandler, "productOutOfQtyErrMessage", productOutOfQtyErrMessage);

        ReflectionTestUtils.setField(customExceptionHandler, "genericErrCode", genericErrCode);
        ReflectionTestUtils.setField(customExceptionHandler, "genericErrMessage", genericErrMessage);

        Product product = new Product();
        product.setId(1);
        product.setName("Apple MacBook");
        product.setBranch("Apple");
        product.setColor("White");
        product.setDescription("13 Inch");
        product.setQtyInStock(2);
        product.setBuyPrice(43000000L);
        product.setCreatedTime(time);
        product.setUpdatedTime(time);

        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productService.search(any())).thenReturn(products);
        when(productService.getProduct(1)).thenReturn(product);
        when(productService.getProduct(2)).thenThrow(new ProductNotFoundException());

        ProductResponse response = new ProductResponse();
        response.setProductId(1);
        response.setQty(3);
        response.setRemainingQty(2);
        response.setPrice(10000L);

        when(productService.buyProduct(any())).thenReturn(response);
    }

    @Test
    public void testSearchProduct() throws Exception {
        mockMvc.perform(post("/api/v1/product/search").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].*", Matchers.hasSize(9)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Apple MacBook")))
                .andExpect(jsonPath("$[0].branch", Matchers.is("Apple")))
                .andExpect(jsonPath("$[0].color", Matchers.is("White")))
                .andExpect(jsonPath("$[0].description", Matchers.is("13 Inch")))
                .andExpect(jsonPath("$[0].qtyInStock", Matchers.is(2)))
                .andExpect(jsonPath("$[0].buyPrice", Matchers.is(43000000)))
                .andExpect(jsonPath("$[0].createdTime", Matchers.is(time.getTime())))
                .andExpect(jsonPath("$[0].updatedTime", Matchers.is(time.getTime())));
    }

    @Test
    public void testGetProduct() throws Exception {
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(9)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Apple MacBook")))
                .andExpect(jsonPath("$.branch", Matchers.is("Apple")))
                .andExpect(jsonPath("$.color", Matchers.is("White")))
                .andExpect(jsonPath("$.description", Matchers.is("13 Inch")))
                .andExpect(jsonPath("$.qtyInStock", Matchers.is(2)))
                .andExpect(jsonPath("$.buyPrice", Matchers.is(43000000)))
                .andExpect(jsonPath("$.createdTime", Matchers.is(time.getTime())))
                .andExpect(jsonPath("$.updatedTime", Matchers.is(time.getTime())));
    }

    @Test
    public void testGetProductNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/product/2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(productNotFoundErrCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(productNotFoundErrMessage)));
    }

    @Test
    public void testBuyProductNotFound() throws Exception {
        when(productService.buyProduct(any())).thenThrow(new ProductNotFoundException());

        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(productNotFoundErrCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(productNotFoundErrMessage)));
    }

    @Test
    public void testBuyProductOutOfQty() throws Exception {
        when(productService.buyProduct(any())).thenThrow(new ProductOutOfQtyException());

        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(productOutOfQtyErrCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(productOutOfQtyErrMessage)));
    }

    @Test
    public void testBuyProduct() throws Exception {
        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(4)))
                .andExpect(jsonPath("$.productId", Matchers.is(1)))
                .andExpect(jsonPath("$.qty", Matchers.is(3)))
                .andExpect(jsonPath("$.remainingQty", Matchers.is(2)))
                .andExpect(jsonPath("$.price", Matchers.is(10000)));
    }

    @Test
    public void testBuyProductInternalServerError() throws Exception {
        when(productService.buyProduct(any())).thenThrow(new RuntimeException("Unit test exception."));

        mockMvc.perform(post("/api/v1/product/buy").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.errorCode", Matchers.is(genericErrCode)))
                .andExpect(jsonPath("$.errorMessage", Matchers.is(genericErrMessage)));
    }
}
