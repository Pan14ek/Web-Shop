package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if problems with transaction . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class InvalidTransactionException extends AppException {

    public InvalidTransactionException(String message) {
        super(message);
    }

    public InvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}