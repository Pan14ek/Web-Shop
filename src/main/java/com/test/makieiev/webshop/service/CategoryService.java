package com.test.makieiev.webshop.service;

import com.test.makieiev.webshop.model.item.Category;

import java.util.List;

/**
 * The service is responsible for operating with category data
 *
 * @author Oleksii_Makieiev1
 */
public interface CategoryService {

    /**
     * The method returns list of categories
     *
     * @return list of categories
     */
    List<Category> getAll();

}
