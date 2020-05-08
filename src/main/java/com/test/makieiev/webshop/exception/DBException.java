package com.test.makieiev.webshop.exception;

/**
 * The custom exception for database problems . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class DBException extends AppException {

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}