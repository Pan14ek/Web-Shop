package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.OrderDao;
import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.NotFoundOrderException;
import com.test.makieiev.webshop.exception.NotKeyException;
import com.test.makieiev.webshop.model.item.Category;
import com.test.makieiev.webshop.model.item.Producer;
import com.test.makieiev.webshop.model.item.Product;
import com.test.makieiev.webshop.model.order.Order;
import com.test.makieiev.webshop.model.order.OrderItem;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.util.constant.CategoryConstant;
import com.test.makieiev.webshop.util.constant.ItemConstant;
import com.test.makieiev.webshop.util.constant.OrderConstant;
import com.test.makieiev.webshop.util.constant.ProducerConstant;
import com.test.makieiev.webshop.util.constant.sql.OrderSqlConstants;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Optional<Order> getOrderByUser(User user, Connection connection) {
        try {
            Order order = new Order();
            PreparedStatement preparedStatement = connection.prepareStatement(OrderSqlConstants.SELECT_ORDER_BY_USER);
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            checkOrderResultSet(resultSet);
            order.setId(resultSet.getLong(OrderConstant.ID_ORDER));
            order.setUser(user);
            order.setState(resultSet.getString(OrderConstant.STATE));
            order.setStatus(resultSet.getString(OrderConstant.STATUS));
            order.setDateTime(resultSet.getTimestamp(OrderConstant.DATE_TIME).toLocalDateTime());
            order.setItemOrders(getListOfOrderItem(order.getId(), connection));
            return Optional.of(order);
        } catch (SQLException e) {
            LOGGER.error("Error with database", e);
            throw new DBException("Error with database", e);
        }
    }

    private void checkOrderResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new NotFoundOrderException("Not found order by user id");
        }
    }

    private List<OrderItem> getListOfOrderItem(long id, Connection connection) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(OrderSqlConstants.SELECT_ORDER_ITEM_BY_ORDER_ID);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orderItems.add(new OrderItem(getProductFromResultSet(resultSet),
                    resultSet.getInt(OrderConstant.AMOUNT),
                    resultSet.getBigDecimal(OrderConstant.PRICE)));
        }
        return orderItems;
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

    @Override
    public boolean addOrder(Order order, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(OrderSqlConstants.INSERT_ORDER_ITEM, PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            preparedStatement.setLong(index++, order.getUser().getId());
            preparedStatement.setString(index++, order.getStatus());
            preparedStatement.setString(index++, order.getState());
            preparedStatement.setTimestamp(index, Timestamp.valueOf(order.getDateTime()));
            preparedStatement.executeUpdate();
            ResultSet keyResultSet = preparedStatement.getGeneratedKeys();
            checkKeyResultSet(keyResultSet);
            order.setId(keyResultSet.getLong(1));
            addOrderItems(order, connection);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Error with database", e);
            throw new DBException("Error with database", e);
        }
    }

    private void checkKeyResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new NotKeyException("Element did not add to database");
        }
    }

    private void addOrderItems(Order order, Connection connection) throws SQLException {
        List<OrderItem> orderItems = order.getItemOrders();
        PreparedStatement preparedStatement = connection.prepareStatement(OrderSqlConstants.INSERT_ORDER_DETAILS);
        for (OrderItem orderItem : orderItems) {
            int index = 1;
            preparedStatement.setLong(index++, orderItem.getProduct().getId());
            preparedStatement.setInt(index++, orderItem.getAmount());
            preparedStatement.setBigDecimal(index++, orderItem.getPrice());
            preparedStatement.setLong(index, order.getId());
            preparedStatement.executeUpdate();
        }
    }

}