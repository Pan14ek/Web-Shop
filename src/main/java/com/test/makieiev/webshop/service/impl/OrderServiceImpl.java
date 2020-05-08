package com.test.makieiev.webshop.service.impl;

import com.test.makieiev.webshop.dao.OrderDao;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.model.order.Order;
import com.test.makieiev.webshop.service.OrderService;

import java.sql.Connection;

public class OrderServiceImpl implements OrderService {

    private final TransactionManager transactionManager;
    private final OrderDao orderDao;

    public OrderServiceImpl(TransactionManager transactionManager, OrderDao orderDao) {
        this.transactionManager = transactionManager;
        this.orderDao = orderDao;
    }

    @Override
    public boolean add(Order order) {
        Connection connection = transactionManager.getConnection();
        return (boolean) transactionManager.doTransaction(() -> orderDao.addOrder(order, connection), connection);
    }

}