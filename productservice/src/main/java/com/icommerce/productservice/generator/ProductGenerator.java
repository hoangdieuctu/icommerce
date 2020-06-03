package com.icommerce.productservice.generator;

import com.icommerce.productservice.model.Product;
import com.icommerce.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "generator")
public class ProductGenerator {

    private Logger logger = LoggerFactory.getLogger(ProductGenerator.class);

    @Autowired
    private ProductRepository productRepository;

    private List<String> products;

    // for binding configuration
    public List<String> getProducts() {
        return products;
    }

    // for binding configuration
    public void setProducts(List<String> products) {
        this.products = products;
    }

    public void generateProducts() {
        if (products == null || products.isEmpty()) {
            return;
        }

        logger.info("Generate products");
        for (String item : products) {
            String[] parts = item.split(",");

            Product product = new Product();
            product.setId(Integer.parseInt(parts[0]));
            product.setName(parts[1]);
            product.setColor(parts[2]);
            product.setBranch(parts[3]);
            product.setDescription(parts[4]);
            product.setBuyPrice(Long.parseLong(parts[5]));
            product.setQtyInStock(Integer.parseInt(parts[6]));

            Date now = new Date();
            product.setCreatedTime(now);
            product.setUpdatedTime(now);

            productRepository.save(product);
        }
    }

}
