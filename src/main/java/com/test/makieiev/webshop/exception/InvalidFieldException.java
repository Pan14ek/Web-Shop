package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if invalid field . The exception extends AppException
 *
 * @author Oleksii_Makieiev1
 */
public class InvalidFieldException extends AppException {

    public InvalidFieldException(String message) {
        super(message);
    }

    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }

}