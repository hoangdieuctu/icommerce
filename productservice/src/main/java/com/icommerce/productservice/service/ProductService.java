package com.icommerce.productservice.service;

import com.icommerce.productservice.dto.request.ProductCriteria;
import com.icommerce.productservice.dto.request.ProductRequest;
import com.icommerce.productservice.dto.response.ProductResponse;
import com.icommerce.productservice.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> search(ProductCriteria criteria);

    Product getProduct(Integer productId);

    ProductResponse buyProduct(ProductRequest request);
}
