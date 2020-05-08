package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if problem with remove operations . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class IllegalRemoveException extends AppException {

    public IllegalRemoveException(String message) {
        super(message);
    }

    public IllegalRemoveException(String message, Throwable cause) {
        super(message, cause);
    }

}