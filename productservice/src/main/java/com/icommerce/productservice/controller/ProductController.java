package com.icommerce.productservice.controller;

import com.icommerce.productservice.dto.request.ProductCriteria;
import com.icommerce.productservice.dto.request.ProductRequest;
import com.icommerce.productservice.dto.response.ProductResponse;
import com.icommerce.productservice.model.Product;
import com.icommerce.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @PostMapping("/search")
    public List<Product> search(@RequestBody ProductCriteria criteria) {
        return productService.search(criteria);
    }

    @ResponseBody
    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable("productId") Integer productId) {
        return productService.getProduct(productId);
    }

    @ResponseBody
    @PostMapping("/buy")
    public ProductResponse buyProduct(@RequestBody ProductRequest request) {
        return productService.buyProduct(request);
    }
}
