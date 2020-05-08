package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if user is had in database . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotUniqueUserException extends AppException {

    public NotUniqueUserException(String message) {
        super(message);
    }

    public NotUniqueUserException(String message, Throwable cause) {
        super(message, cause);
    }

}