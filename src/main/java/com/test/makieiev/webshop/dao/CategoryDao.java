package com.test.makieiev.webshop.dao;


import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.NotFoundCategoryException;
import com.test.makieiev.webshop.model.item.Category;

import java.sql.Connection;
import java.util.List;

/**
 * Interface which implemented dao for category
 *
 * @author Oleksii_Makieiev1
 */
public interface CategoryDao {

    /**
     * The methods returns list of categories
     *
     * @param connection is connection with database
     * @return list with all categories
     * @throws DBException               if errors with database
     * @throws NotFoundCategoryException if category did not found
     */
    List<Category> getAllCategories(Connection connection);

}