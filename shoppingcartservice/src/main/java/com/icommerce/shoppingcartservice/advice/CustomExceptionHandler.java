package com.icommerce.shoppingcartservice.advice;

import com.icommerce.shoppingcartservice.dto.response.ErrorResponse;
import com.icommerce.shoppingcartservice.exception.DownstreamException;
import com.icommerce.shoppingcartservice.exception.ShoppingCartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @Value("${error.shoppingCart.code}")
    private int shoppingCartErrCode;

    @Value("${error.shoppingCart.message}")
    private String shoppingCartErrMessage;

    @Value("${error.exception.code}")
    private int genericErrCode;

    @Value("${error.exception.message}")
    private String genericErrMessage;

    @ResponseBody
    @ExceptionHandler(value = DownstreamException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDownstreamException(DownstreamException exception) {
        return exception.getErrorResponse();
    }

    @ResponseBody
    @ExceptionHandler(value = ShoppingCartException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleShoppingCartException(ShoppingCartException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(shoppingCartErrCode);
        response.setErrorMessage(shoppingCartErrMessage);

        return response;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        logger.error("Internal error.", exception);

        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(genericErrCode);
        response.setErrorMessage(genericErrMessage);

        return response;
    }
}
