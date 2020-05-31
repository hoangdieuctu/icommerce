package com.icommerce.productservice.generator;

import com.icommerce.productservice.model.Product;
import com.icommerce.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProductGenerator {

    private Logger logger = LoggerFactory.getLogger(ProductGenerator.class);

    @Autowired
    private ProductRepository productRepository;

    @Value("${generator.products:#{null}}")
    private String products;

    @PostConstruct
    public void generateProducts() {
        if (products == null || products.isEmpty()) {
            return;
        }

        logger.info("Generate products");
        String[] items = products.split(";");
        for (String item : items) {
            String[] parts = item.split(",");

            Product product = new Product();
            product.setId(Integer.parseInt(parts[0]));
            product.setName(parts[1]);
            product.setColor(parts[2]);
            product.setBranch(parts[3]);
            product.setDescription(parts[4]);
            product.setBuyPrice(Long.parseLong(parts[5]));
            product.setQtyInStock(Integer.parseInt(parts[6]));

            productRepository.save(product);
        }
    }

}
