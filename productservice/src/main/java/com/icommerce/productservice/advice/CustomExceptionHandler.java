package com.icommerce.productservice.advice;

import com.icommerce.productservice.dto.response.ErrorResponse;
import com.icommerce.productservice.exception.ProductNotFoundException;
import com.icommerce.productservice.exception.ProductOutOfQtyException;
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

    @Value("${error.productNotFound.code}")
    private int productNotFoundErrCode;

    @Value("${error.productNotFound.message}")
    private String productNotFoundErrMessage;

    @Value("${error.exception.code}")
    private int genericErrCode;

    @Value("${error.exception.message}")
    private String genericErrMessage;

    @Value("${error.productOutOfQty.code}")
    private int productOutOfQtyErrCode;

    @Value("${error.productOutOfQty.message}")
    private String productOutOfQtyErrMessage;

    @ResponseBody
    @ExceptionHandler(value = ProductNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(productNotFoundErrCode);
        response.setErrorMessage(productNotFoundErrMessage);

        return response;
    }

    @ResponseBody
    @ExceptionHandler(value = ProductOutOfQtyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductOutOfQtyException(ProductOutOfQtyException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(productOutOfQtyErrCode);
        response.setErrorMessage(productOutOfQtyErrMessage);

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
