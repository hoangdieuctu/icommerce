package com.icommerce.authenticationservice.repository;

import com.icommerce.authenticationservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
