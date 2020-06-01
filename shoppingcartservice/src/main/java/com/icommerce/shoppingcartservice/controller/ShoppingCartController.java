package com.icommerce.shoppingcartservice.controller;

import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.model.ShoppingCart;
import com.icommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ResponseBody
    @GetMapping
    public ShoppingCart getShoppingCart(@RequestHeader("userId") int userId) {
        return shoppingCartService.getCurrentShoppingCart(userId);
    }

    @ResponseBody
    @PostMapping
    public ShoppingCart addProduct(@RequestHeader("userId") int userId, @RequestBody ProductRequest request) {
        return shoppingCartService.addProduct(userId, request);
    }
}
