package com.icommerce.shoppingcartservice.service;

import com.icommerce.shoppingcartservice.dto.request.ProductRequest;
import com.icommerce.shoppingcartservice.dto.response.ProductResponse;

public interface ProductService {
    ProductResponse buyProduct(ProductRequest request);
}
