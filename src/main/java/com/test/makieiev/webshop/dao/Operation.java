package com.test.makieiev.webshop.dao;

/**
 * The interface Operation has method operate for operating operation with database
 *
 * @param <T> determines the type
 * @author Oleksii_Makieiev1
 */
public interface Operation<T> {

    /**
     * The method operate with transaction manager which take information from dao and connection
     *
     * @return special object
     */
    T operate();

}