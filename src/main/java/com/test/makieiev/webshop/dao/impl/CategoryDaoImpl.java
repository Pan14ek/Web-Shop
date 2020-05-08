package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.CategoryDao;
import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.NotFoundCategoryException;
import com.test.makieiev.webshop.model.item.Category;
import com.test.makieiev.webshop.util.constant.CategoryConstant;
import com.test.makieiev.webshop.util.constant.sql.CategorySqlConstants;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private static final Logger LOGGER = Logger.getLogger(CategoryDaoImpl.class);

    @Override
    public List<Category> getAllCategories(Connection connection) {
        try {
            List<Category> categories = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(CategorySqlConstants.SELECT_ALL_CATEGORY);
            ResultSet resultSet = preparedStatement.executeQuery();
            checkCategoryResultSet(resultSet, categories);
            while (resultSet.next()) {
                categories.add(getCategoryFromResultSet(resultSet));
            }
            return categories;
        } catch (SQLException e) {
            LOGGER.error("Error with database", e);
            throw new DBException("Error with database", e);
        }
    }

    private void checkCategoryResultSet(ResultSet resultSet, List<Category> categories) throws SQLException {
        if (!resultSet.next()) {
            throw new NotFoundCategoryException("Category did not found");
        }
        categories.add(getCategoryFromResultSet(resultSet));
    }

    private Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong(CategoryConstant.ID_CATEGORY));
        category.setTitle(resultSet.getString(CategoryConstant.CATEGORY_NAME));
        category.setDescription(resultSet.getString(CategoryConstant.DESCRIPTION));
        return category;
    }

}