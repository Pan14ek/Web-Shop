package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if producer is not found in database . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotFoundProducerException extends AppException {

    public NotFoundProducerException(String message) {
        super(message);
    }

    public NotFoundProducerException(String message, Throwable cause) {
        super(message, cause);
    }

}