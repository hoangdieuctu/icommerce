package com.icommerce.productservice.service;

import com.icommerce.productservice.dto.request.ProductRequest;
import com.icommerce.productservice.dto.response.ProductResponse;
import com.icommerce.productservice.exception.ProductNotFoundException;
import com.icommerce.productservice.exception.ProductOutOfQtyException;
import com.icommerce.productservice.model.Product;
import com.icommerce.productservice.repository.ProductRepository;
import com.icommerce.productservice.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Product product;

    @Before
    public void setup() {
        when(product.getQtyInStock()).thenReturn(2);
        when(product.getBuyPrice()).thenReturn(10000L);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.findById(2)).thenReturn(Optional.empty());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductNotFound() {
        productService.getProduct(2);
    }

    @Test
    public void testGetProduct() {
        Product product = productService.getProduct(1);
        Assert.assertEquals(this.product, product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testBuyProductNotFound() {
        ProductRequest request = new ProductRequest();
        request.setProductId(2);
        request.setQty(1);

        productService.buyProduct(request);
    }

    @Test(expected = ProductOutOfQtyException.class)
    public void testBuyProductOutOfQty() {
        ProductRequest request = new ProductRequest();
        request.setProductId(1);
        request.setQty(3);

        productService.buyProduct(request);
    }

    @Test
    public void testBuyProduct() {
        ProductRequest request = new ProductRequest();
        request.setProductId(1);
        request.setQty(2);

        ProductResponse response = productService.buyProduct(request);

        Assert.assertEquals(1, response.getProductId());
        Assert.assertEquals(2, response.getQty());
        Assert.assertEquals(0, response.getRemainingQty());
        Assert.assertEquals(10000L, response.getPrice(), 0);

        verify(product).setQtyInStock(0);
        verify(productRepository).save(product);
    }
}
