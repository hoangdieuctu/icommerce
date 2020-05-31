package com.icommerce.shoppingcartservice.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShoppingStatus status;

    @JoinColumn(name = "shoppingCartId")
    @OneToMany(fetch = FetchType.EAGER)
    private List<ProductCart> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ShoppingStatus getStatus() {
        return status;
    }

    public void setStatus(ShoppingStatus status) {
        this.status = status;
    }

    public List<ProductCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCart> products) {
        this.products = products;
    }
}
