package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if problem with update operations . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class IllegalUpdateException extends AppException {

    public IllegalUpdateException(String message) {
        super(message);
    }

    public IllegalUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

}