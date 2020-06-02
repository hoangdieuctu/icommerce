package com.icommerce.productservice.service;

import com.icommerce.productservice.dto.request.Order;
import com.icommerce.productservice.dto.request.PriceCriteria;
import com.icommerce.productservice.dto.request.ProductCriteria;
import com.icommerce.productservice.dto.request.Sort;
import com.icommerce.productservice.model.Product;
import com.icommerce.productservice.repository.ProductRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceSpringTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private void addProduct(String name, String branch, String color, Long price, Integer qtyInStock) {
        Product product = new Product();
        product.setName(name);
        product.setBranch(branch);
        product.setColor(color);
        product.setBuyPrice(price);
        product.setQtyInStock(qtyInStock);

        productRepository.save(product);
    }

    @After
    public void teardown() {
        productRepository.deleteAll();
    }

    @Before
    public void setup() {
        addProduct("MacBook 2017", "Apple", "White", 43000000L, 5);
        addProduct("MacBook 2013", "Apple", "White", 30000000L, 2);
        addProduct("Acer Aspire", "Acer", "Black", 50000000L, 10);
    }

    @Test
    public void testSearchProductSortByNameAsDefault() {
        Sort sort = new Sort();
        sort.setOrderBy(Order.desc);

        ProductCriteria criteria = new ProductCriteria();
        criteria.setSort(sort);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(3, products.size());

        Assert.assertEquals("MacBook 2017", products.get(0).getName());
        Assert.assertEquals("MacBook 2013", products.get(1).getName());
        Assert.assertEquals("Acer Aspire", products.get(2).getName());
    }

    @Test
    public void testSearchProductSortByPriceDesc() {
        Sort sort = new Sort();
        sort.setSortBy("buyPrice");
        sort.setOrderBy(Order.desc);

        ProductCriteria criteria = new ProductCriteria();
        criteria.setSort(sort);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(3, products.size());

        Assert.assertEquals("Acer Aspire", products.get(0).getName());
        Assert.assertEquals("MacBook 2017", products.get(1).getName());
        Assert.assertEquals("MacBook 2013", products.get(2).getName());
    }

    @Test
    public void testSearchProductByName() {
        ProductCriteria criteria = new ProductCriteria();
        criteria.setName("Mac");

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(2, products.size());

        Assert.assertEquals("MacBook 2017", products.get(0).getName());
        Assert.assertEquals("MacBook 2013", products.get(1).getName());
    }

    @Test
    public void testSearchProductByNameSortByPriceAsc() {
        Sort sort = new Sort();
        sort.setSortBy("buyPrice");

        ProductCriteria criteria = new ProductCriteria();
        criteria.setName("Mac");
        criteria.setSort(sort);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(2, products.size());

        Assert.assertEquals("MacBook 2013", products.get(0).getName());
        Assert.assertEquals("MacBook 2017", products.get(1).getName());
    }

    @Test
    public void testSearchProductByColor() {
        ProductCriteria criteria = new ProductCriteria();
        criteria.setColor("Black");

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(1, products.size());

        Assert.assertEquals("Acer Aspire", products.get(0).getName());
    }

    @Test
    public void testSearchProductByBranch() {
        ProductCriteria criteria = new ProductCriteria();
        criteria.setBranch("Acer");

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(1, products.size());

        Assert.assertEquals("Acer Aspire", products.get(0).getName());
    }

    @Test
    public void testSearchProductByPrice() {
        PriceCriteria price = new PriceCriteria();
        price.setFrom(40000000L);
        price.setTo(60000000L);

        ProductCriteria criteria = new ProductCriteria();
        criteria.setPrice(price);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(2, products.size());

        Assert.assertEquals("MacBook 2017", products.get(0).getName());
        Assert.assertEquals("Acer Aspire", products.get(1).getName());
    }

    @Test
    public void testSearchProductByPriceSortByNameAsc() {
        PriceCriteria price = new PriceCriteria();
        price.setFrom(40000000L);
        price.setTo(60000000L);

        Sort sort = new Sort();
        sort.setSortBy("name");
        sort.setOrderBy(Order.asc);

        ProductCriteria criteria = new ProductCriteria();
        criteria.setPrice(price);
        criteria.setSort(sort);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(2, products.size());

        Assert.assertEquals("Acer Aspire", products.get(0).getName());
        Assert.assertEquals("MacBook 2017", products.get(1).getName());
    }

    @Test
    public void testSearchProductByNameAndPrice() {
        PriceCriteria price = new PriceCriteria();
        price.setFrom(40000000L);
        price.setTo(60000000L);

        ProductCriteria criteria = new ProductCriteria();
        criteria.setName("Mac");
        criteria.setPrice(price);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(1, products.size());

        Assert.assertEquals("MacBook 2017", products.get(0).getName());
    }

    @Test
    public void testSearchProductByNamePriceAndColor() {
        PriceCriteria price = new PriceCriteria();
        price.setFrom(40000000L);
        price.setTo(60000000L);

        ProductCriteria criteria = new ProductCriteria();
        criteria.setName("Mac");
        criteria.setColor("White");
        criteria.setPrice(price);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(1, products.size());

        Assert.assertEquals("MacBook 2017", products.get(0).getName());
    }

    @Test
    public void testSearchProductByNamePriceAndColorNotFound() {
        PriceCriteria price = new PriceCriteria();
        price.setFrom(40000000L);
        price.setTo(60000000L);

        ProductCriteria criteria = new ProductCriteria();
        criteria.setName("Mac");
        criteria.setColor("Black");
        criteria.setPrice(price);

        List<Product> products = productService.search(criteria);
        Assert.assertEquals(0, products.size());
    }
}
