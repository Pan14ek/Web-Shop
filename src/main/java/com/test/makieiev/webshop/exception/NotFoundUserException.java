package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if user is not found in database . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotFoundUserException extends AppException {

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }

}