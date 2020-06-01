package com.icommerce.shoppingcartservice.service;

import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.dto.response.ProductResponse;
import com.icommerce.shoppingcartservice.model.ProductCart;
import com.icommerce.shoppingcartservice.model.ShoppingCart;
import com.icommerce.shoppingcartservice.model.ShoppingStatus;
import com.icommerce.shoppingcartservice.repository.ShoppingCartRepository;
import com.icommerce.shoppingcartservice.service.impl.ShoppingCartServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ProductService productService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCart shoppingCart;

    @Mock
    private ProductResponse response;

    private int userId = 1;
    private long price = 30000L;
    private ProductRequest request;

    @Before
    public void setup() {
        request = new ProductRequest();
        request.setProductId(1);
        request.setQty(2);

        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        shoppingCarts.add(shoppingCart);

        when(shoppingCartRepository.findShoppingCartByStatusAndUserId(ShoppingStatus.NEW, userId)).thenReturn(shoppingCarts);
        when(productService.buyProduct(request)).thenReturn(response);
        when(response.getPrice()).thenReturn(price);
    }

    @Test
    public void testGetCurrentShoppingCart() {
        ShoppingCart currentShoppingCart = shoppingCartService.getCurrentShoppingCart(userId);
        Assert.assertEquals(shoppingCart, currentShoppingCart);
    }

    @Test
    public void testAddProduct() {
        ArgumentCaptor<ProductCart> captor = ArgumentCaptor.forClass(ProductCart.class);
        ShoppingCart returnShoppingCart = shoppingCartService.addProduct(userId, request);

        Assert.assertEquals(shoppingCart, returnShoppingCart);
        verify(shoppingCart).addProduct(captor.capture());
        verify(shoppingCartRepository).save(shoppingCart);

        ProductCart productCart = captor.getValue();
        Assert.assertEquals(1, productCart.getProductId(), 0);
        Assert.assertEquals(2, productCart.getQty(), 0);
        Assert.assertEquals(30000, productCart.getPriceOfEach(), 0);
    }

    @Test
    public void testAddProductFirstTime() {
        ShoppingCart returnShoppingCart = shoppingCartService.addProduct(2, request);

        Assert.assertNotEquals(shoppingCart, returnShoppingCart);

        Assert.assertEquals(2, returnShoppingCart.getUserId(), 0);
        Assert.assertEquals(ShoppingStatus.NEW, returnShoppingCart.getStatus());
        verify(shoppingCartRepository).save(returnShoppingCart);

        List<ProductCart> products = returnShoppingCart.getProducts();
        Assert.assertEquals(1, products.size());
        ProductCart productCart = products.get(0);

        Assert.assertEquals(1, productCart.getProductId(), 0);
        Assert.assertEquals(2, productCart.getQty(), 0);
        Assert.assertEquals(30000, productCart.getPriceOfEach(), 0);
    }
}
