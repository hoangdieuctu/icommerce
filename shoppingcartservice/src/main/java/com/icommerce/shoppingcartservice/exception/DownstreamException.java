package com.icommerce.shoppingcartservice.exception;

import com.icommerce.shoppingcartservice.dto.response.ErrorResponse;

public class DownstreamException extends ShoppingCartException {

    private ErrorResponse errorResponse;

    public DownstreamException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
