package com.test.makieiev.webshop.service;

import com.test.makieiev.webshop.model.order.Order;

/**
 * The service is responsible for operating with order data
 *
 * @author Oleksii_Makieiev1
 */
public interface OrderService {

    /**
     * The method adds order to database
     *
     * @param order is the converted and valid order from dto
     * @return true if order adds successfully to database
     */
    boolean add(Order order);

}