package com.test.makieiev.webshop.exception;

/**
 * The main custom exception which extend runtime exception
 *
 * @author Oleksii_Makieiev1
 */
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

}