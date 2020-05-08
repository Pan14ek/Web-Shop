package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if element did not add to database and generate special key . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotKeyException extends AppException {

    public NotKeyException(String message) {
        super(message);
    }

    public NotKeyException(String message, Throwable cause) {
        super(message, cause);
    }

}