package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if item is duplicate . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotUniqueItemException extends AppException {

    public NotUniqueItemException(String message) {
        super(message);
    }

    public NotUniqueItemException(String message, Throwable cause) {
        super(message, cause);
    }

}