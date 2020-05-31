package com.icommerce.shoppingcartservice.service;

import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart getByUser(Integer userId);
    ShoppingCart addProduct(Integer userId, ProductRequest request);
}
