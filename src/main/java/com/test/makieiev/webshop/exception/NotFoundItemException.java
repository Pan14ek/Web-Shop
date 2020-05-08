package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if item is not found in database . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotFoundItemException extends AppException {

    public NotFoundItemException(String message) {
        super(message);
    }

    public NotFoundItemException(String message, Throwable cause) {
        super(message, cause);
    }

}