package com.test.makieiev.webshop.exception;

/**
 * The custom exception throws if problem with parsing xml document . The exception extended AppException
 *
 * @author Oleksii_Makieiev1
 */
public class InvalidParseXmlException extends AppException {

    public InvalidParseXmlException(String message) {
        super(message);
    }

    public InvalidParseXmlException(String message, Throwable cause) {
        super(message, cause);
    }

}