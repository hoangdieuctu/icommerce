package com.icommerce.shoppingcartservice.service.impl;

import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.dto.response.ProductResponse;
import com.icommerce.shoppingcartservice.exception.ShoppingCartException;
import com.icommerce.shoppingcartservice.model.ProductCart;
import com.icommerce.shoppingcartservice.model.ShoppingCart;
import com.icommerce.shoppingcartservice.model.ShoppingStatus;
import com.icommerce.shoppingcartservice.repository.ShoppingCartRepository;
import com.icommerce.shoppingcartservice.service.ProductService;
import com.icommerce.shoppingcartservice.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart getCurrentShoppingCart(Integer userId) {
        List<ShoppingCart> carts = shoppingCartRepository.findShoppingCartByStatusAndUserId(ShoppingStatus.NEW, userId);
        return carts == null || carts.isEmpty() ? null : carts.get(0);
    }

    @Override
    public ShoppingCart addProduct(Integer userId, ProductRequest request) {
        logger.info("Buy product: {}", request);

        ProductResponse response = productService.buyProduct(request);
        logger.info("Buy product response: {}", response);

        Date now = new Date();

        ProductCart productCart = new ProductCart();
        productCart.setProductId(request.getProductId());
        productCart.setQty(request.getQty());
        productCart.setPriceOfEach(response.getPrice());
        productCart.setCreatedTime(now);

        ShoppingCart shoppingCart = getCurrentShoppingCart(userId);
        if (shoppingCart == null) {
            logger.debug("Shopping cart not found, create a new one.");

            shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setStatus(ShoppingStatus.NEW);
            shoppingCart.setCreatedTime(now);

            List<ProductCart> productCarts = new ArrayList<>();
            productCarts.add(productCart);

            shoppingCart.setProducts(productCarts);
        } else {
            shoppingCart.addProduct(productCart);
        }

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }

}
