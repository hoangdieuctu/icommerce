package com.icommerce.shoppingcartservice.repository;

import com.icommerce.shoppingcartservice.model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
}
