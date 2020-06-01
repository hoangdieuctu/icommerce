package com.icommerce.shoppingcartservice.exception;

public class ShoppingCartException extends RuntimeException {
    public ShoppingCartException() {
        super();
    }

    public ShoppingCartException(String message) {
        super(message);
    }

    public ShoppingCartException(String message, Throwable cause) {
        super(message, cause);
    }
}
