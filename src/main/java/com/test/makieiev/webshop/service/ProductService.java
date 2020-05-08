package com.test.makieiev.webshop.service;

import com.test.makieiev.webshop.model.item.Product;

import java.util.List;
import java.util.Optional;

/**
 * The service is responsible for operating with items data
 *
 * @author Oleksii_Makieiev1
 */
public interface ProductService {

    Optional<Product> getById(long id);

    /**
     * The method returns list of electronics
     *
     * @return list of electronics
     */
    List<Product> getAll();

    /**
     * The method returns list of sorted and filtered electronics
     *
     * @param query is special query which formatted with query builder
     * @return list with filtered and sorted electronics
     */
    List<Product> getAllSortedAndFiltered(String query);

    /**
     * The method returns amount of items .
     *
     * @return amount of items
     */
    long getAmount();

}