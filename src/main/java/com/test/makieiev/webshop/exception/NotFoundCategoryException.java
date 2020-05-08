package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if category is not found in database . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class NotFoundCategoryException extends AppException {

    public NotFoundCategoryException(String message) {
        super(message);
    }

    public NotFoundCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

}