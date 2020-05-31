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
    public ShoppingCart getShoppingCart() {
        int userId = 1;
        return shoppingCartService.getByUser(userId);
    }

    @ResponseBody
    @PostMapping
    public ShoppingCart addProduct(@RequestBody ProductRequest request) {
        int userId = 1;
        return shoppingCartService.addProduct(userId, request);
    }
}
