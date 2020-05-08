package com.test.makieiev.webshop.dao;

import com.test.makieiev.webshop.model.order.Order;
import com.test.makieiev.webshop.model.user.User;

import java.sql.Connection;
import java.util.Optional;

/**
 * Interface which implemented dao for order
 *
 * @author Oleksii_Makieiev1
 */
public interface OrderDao {

    /**
     * The method returns order which searching by user
     *
     * @param user       is the parameter which it is use for searching order
     * @param connection is connection with database
     * @return optional order
     */
    Optional<Order> getOrderByUser(User user, Connection connection);

    /**
     * The method add order to database
     *
     * @param order      is order which we should add to database
     * @param connection is connection with database
     * @return true if add successfully or throws exception NotUniqueOrderException
     */
    boolean addOrder(Order order, Connection connection);

}