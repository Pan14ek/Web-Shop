package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if order is not found in database . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotFoundOrderException extends AppException {

    public NotFoundOrderException(String message) {
        super(message);
    }

    public NotFoundOrderException(String message, Throwable cause) {
        super(message, cause);
    }

}