package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.ProductDao;
import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.IllegalRemoveException;
import com.test.makieiev.webshop.exception.IllegalUpdateException;
import com.test.makieiev.webshop.exception.NotFoundItemException;
import com.test.makieiev.webshop.exception.NotUniqueItemException;
import com.test.makieiev.webshop.model.item.Category;
import com.test.makieiev.webshop.model.item.Producer;
import com.test.makieiev.webshop.model.item.Product;
import com.test.makieiev.webshop.util.constant.CategoryConstant;
import com.test.makieiev.webshop.util.constant.ItemConstant;
import com.test.makieiev.webshop.util.constant.ProducerConstant;
import com.test.makieiev.webshop.util.constant.sql.ItemSqlConstants;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {

    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

    @Override
    public Optional<Product> getProductById(long id, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemSqlConstants.SELECT_ELECTRONIC_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getOptionalProduct(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NotFoundItemException("Item did not found", e);
        }
    }

    @Override
    public Optional<Product> getProductByTitle(String title, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemSqlConstants.SELECT_ELECTRONIC_BY_TITLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getOptionalProduct(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NotFoundItemException("Item did not found", e);
        }
    }

    @Override
    public boolean addProduct(Product product, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemSqlConstants.INSERT_ELECTRONIC);
            int index = 1;
            preparedStatement.setString(index++, product.getTitle());
            preparedStatement.setBigDecimal(index++, product.getPrice());
            preparedStatement.setLong(index++, product.getCategory().getId());
            preparedStatement.setString(index++, product.getImageLink());
            preparedStatement.setLong(index++, product.getProducer().getId());
            preparedStatement.setString(index++, product.getDescription());
            preparedStatement.setDouble(index, product.getScreenDiagonal());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NotUniqueItemException("Item is already in database with this login ! " + product, e);
        }
    }

    @Override
    public boolean updateProduct(Product product, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemSqlConstants.UPDATE_ELECTRONIC);
            int index = 1;
            preparedStatement.setString(index++, product.getTitle());
            preparedStatement.setBigDecimal(index++, product.getPrice());
            preparedStatement.setLong(index++, product.getCategory().getId());
            preparedStatement.setString(index++, product.getImageLink());
            preparedStatement.setLong(index++, product.getProducer().getId());
            preparedStatement.setString(index++, product.getDescription());
            preparedStatement.setDouble(index++, product.getScreenDiagonal());
            preparedStatement.setLong(index, product.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalUpdateException("Item cannot update ! " + product, e);
        }
    }

    @Override
    public boolean removeProduct(long id, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemSqlConstants.DELETE_ELECTRONIC);
            preparedStatement.setLong(1, id);
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalRemoveException("Item cannot remove", e);
        }
    }

    @Override
    public List<Product> getAllProducts(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemSqlConstants.SELECT_ALL_ELECTRONICS);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractProducts(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NotFoundItemException("Items did not found", e);
        }
    }

    @Override
    public List<Product> getFilteredAndSortedProducts(String query, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractProducts(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NotFoundItemException("Items did not found", e);
        }
    }

    @Override
    public long getProductsAmount(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemSqlConstants.SELECT_AMOUNT_OF_ITEMS);
            ResultSet resultSet = preparedStatement.executeQuery();
            checkAmountResult(resultSet);
            return resultSet.getLong(ItemConstant.AMOUNT);
        } catch (SQLException e) {
            LOGGER.error("Error with database", e);
            throw new DBException("Error with database", e);
        }
    }

    private void checkAmountResult(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new NotFoundItemException("Items did not found");
        }
    }

    private List<Product> extractProducts(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(getProductFromResultSet(resultSet));
        }
        return products;
    }

    private Optional<Product> getOptionalProduct(ResultSet resultSet) throws SQLException {
        Optional<Product> electronic = Optional.empty();
        if (resultSet.next()) {
            electronic = Optional.of(getProductFromResultSet(resultSet));
        }
        return electronic;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        Producer producer = new Producer();
        Category category = new Category();
        product.setId(resultSet.getLong(ItemConstant.ID_ELECTRONIC));
        product.setTitle(resultSet.getString(ItemConstant.ELECTRONIC_NAME));
        product.setDescription(resultSet.getString(ItemConstant.DESCRIPTION));
        product.setPrice(resultSet.getBigDecimal(ItemConstant.PRICE));
        product.setImageLink(resultSet.getString(ItemConstant.IMAGE_LINK));
        product.setScreenDiagonal(resultSet.getDouble(ItemConstant.SCREEN_DIAGONAL));
        producer.setId(resultSet.getLong(ProducerConstant.ID_PRODUCERS));
        producer.setTitle(resultSet.getString(ProducerConstant.TITLE));
        producer.setCity(resultSet.getString(ProducerConstant.CITY));
        producer.setCountry(resultSet.getString(ProducerConstant.COUNTRY));
        category.setId(resultSet.getLong(CategoryConstant.ID_CATEGORY));
        category.setTitle(resultSet.getString(CategoryConstant.CATEGORY_NAME));
        category.setDescription(resultSet.getString(CategoryConstant.DESCRIPTION));
        product.setProducer(producer);
        product.setCategory(category);
        return product;
    }

}