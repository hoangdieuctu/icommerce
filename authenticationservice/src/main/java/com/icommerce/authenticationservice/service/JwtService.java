package com.icommerce.authenticationservice.service;

public interface JwtService {

    String generateToken(Integer userId);

}
