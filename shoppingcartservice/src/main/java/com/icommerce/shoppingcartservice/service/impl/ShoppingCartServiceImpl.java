package com.icommerce.shoppingcartservice.service.impl;

import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.model.ShoppingCart;
import com.icommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Override
    public ShoppingCart getByUser(Integer userId) {
        return null;
    }

    @Override
    public ShoppingCart addProduct(Integer userId, ProductRequest request) {
        return null;
    }
}
