package com.test.makieiev.webshop.dao;


import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.IllegalRemoveException;
import com.test.makieiev.webshop.exception.IllegalUpdateException;
import com.test.makieiev.webshop.exception.NotFoundItemException;
import com.test.makieiev.webshop.exception.NotUniqueItemException;
import com.test.makieiev.webshop.model.item.Product;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Interface which implemented dao for item
 *
 * @author Oleksii_Makieiev1
 */
public interface ProductDao {

    /**
     * This method returns electronic which searching by id
     *
     * @param id         is parameter which it is use for searching electronic
     * @param connection is connection with database
     * @return optional electronic
     */
    Optional<Product> getProductById(long id, Connection connection);

    /**
     * This method returns electronic which searching by title
     *
     * @param title      is parameter which it is use for searching electronic
     * @param connection is connection with database
     * @return optional electronic
     */
    Optional<Product> getProductByTitle(String title, Connection connection);

    /**
     * The method add electronic to database
     *
     * @param product    is electronic which we should add to database
     * @param connection is connection with database
     * @return true if add successfully or throws exception NotUniqueItemException
     * @throws NotUniqueItemException if electronic is already in database with this electronic name
     */
    boolean addProduct(Product product, Connection connection);

    /**
     * The method update electronic in database
     *
     * @param product    is electronic which should be updated to the database
     * @param connection is connection with database
     * @return true if updated successfully or throws IllegalUpdateException
     * @throws IllegalUpdateException if electronic cannot update
     */
    boolean updateProduct(Product product, Connection connection);

    /**
     * The method removed from database
     *
     * @param id         is parameter which it is use for searching electronic and remove electronic with this id
     * @param connection is connection with database
     * @return true if removed successfully or throws IllegalRemoveException
     * @throws IllegalRemoveException if user cannot remove
     */
    boolean removeProduct(long id, Connection connection);

    /**
     * The method returns list of electronics
     *
     * @param connection is connection with database
     * @return list with all electronics
     * @throws DBException           if errors with database
     * @throws NotFoundItemException if users did not found
     */
    List<Product> getAllProducts(Connection connection);

    /**
     * The method returns list of sorted and filtered electronics
     *
     * @param query      is special query which formatted with query builder
     * @param connection is connection with database
     * @return list with filtered and sorted electronics
     * @throws DBException           if errors with database
     * @throws NotFoundItemException if users did not found
     */
    List<Product> getFilteredAndSortedProducts(String query, Connection connection);

    /**
     * The method returns amount of items .
     *
     * @param connection is connection with database
     * @return amount of items
     */
    long getProductsAmount(Connection connection);

}