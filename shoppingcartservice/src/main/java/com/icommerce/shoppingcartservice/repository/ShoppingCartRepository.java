package com.icommerce.shoppingcartservice.repository;

import com.icommerce.shoppingcartservice.model.ShoppingCart;
import com.icommerce.shoppingcartservice.model.ShoppingStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {

    List<ShoppingCart> findShoppingCartByStatusAndUserId(ShoppingStatus status, Integer userId);

}
