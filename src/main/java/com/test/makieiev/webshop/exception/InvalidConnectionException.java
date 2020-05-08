package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if problem with connection . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class InvalidConnectionException extends AppException {

    public InvalidConnectionException(String message) {
        super(message);
    }

    public InvalidConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}