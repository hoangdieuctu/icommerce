package com.icommerce.productservice.service.impl;

import com.icommerce.productservice.dto.request.*;
import com.icommerce.productservice.dto.response.ProductResponse;
import com.icommerce.productservice.exception.ProductOutOfQtyException;
import com.icommerce.productservice.exception.ProductNotFoundException;
import com.icommerce.productservice.model.Product;
import com.icommerce.productservice.repository.ProductRepository;
import com.icommerce.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    private String contains(String value) {
        return "%" + value + "%";
    }

    @Override
    public List<Product> search(ProductCriteria criteria) {
        List<Product> products = productRepository.findAll((Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // for user typing, search like
            String name = criteria.getName();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), contains(name))));
            }

            // from the select box, search equal
            String branch = criteria.getBranch();
            if (branch != null && !branch.isEmpty()) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("branch"), branch)));
            }

            // from the select box, search equal
            String color = criteria.getColor();
            if (color != null && !color.isEmpty()) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("color"), color)));
            }

            // from the select box in range
            PriceCriteria price = criteria.getPrice();
            if (price != null) {
                Long from = price.getFrom();
                if (from != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("buyPrice"), from));
                }

                Long to = price.getTo();
                if (to != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get("buyPrice"), to));
                }
            }

            // allow sort asc/desc of a field
            Sort sort = criteria.getSort();
            if (sort != null) {
                String sortBy = sort.getSortBy();
                if (sortBy == null || sortBy.isEmpty()) {
                    sortBy = "name"; // default sort by
                }

                Order orderBy = sort.getOrderBy();
                if (orderBy == null) {
                    orderBy = Order.asc; // default order by
                }

                if (Order.asc.equals(orderBy)) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortBy)));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortBy)));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return products;
    }

    @Override
    public Product getProduct(Integer productId) {
        Optional<Product> optProduct = productRepository.findById(productId);
        if (!optProduct.isPresent()) {
            throw new ProductNotFoundException();
        }

        return optProduct.get();
    }

    @Override
    public ProductResponse buyProduct(ProductRequest request) {
        logger.info("Buy product: {}", request);

        Optional<Product> optProduct = productRepository.findById(request.getProductId());
        if (!optProduct.isPresent()) {
            logger.warn("Product not found: {}", request.getProductId());
            throw new ProductNotFoundException();
        }

        Product product = optProduct.get();
        int qtyInStock = product.getQtyInStock();

        if (qtyInStock < request.getQty()) {
            logger.warn("Out of qty: {}", request.getProductId());
            throw new ProductOutOfQtyException();
        }

        int remainingQty = qtyInStock - request.getQty();

        product.setQtyInStock(remainingQty);
        product.setUpdatedTime(new Date());
        productRepository.save(product);

        ProductResponse response = new ProductResponse();
        response.setProductId(request.getProductId());
        response.setQty(request.getQty());
        response.setRemainingQty(remainingQty);
        response.setPrice(product.getBuyPrice());

        return response;
    }
}
