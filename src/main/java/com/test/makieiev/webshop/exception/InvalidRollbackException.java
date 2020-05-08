package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if problems with rollback . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class InvalidRollbackException extends AppException {

    public InvalidRollbackException(String message) {
        super(message);
    }

    public InvalidRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

}