package com.test.makieiev.webshop.service.impl;

import com.test.makieiev.webshop.dao.ProducerDao;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.model.item.Producer;
import com.test.makieiev.webshop.service.ProducerService;

import java.sql.Connection;
import java.util.List;

public class ProducerServiceImpl implements ProducerService {

    private final ProducerDao producerDao;
    private final TransactionManager transactionManager;

    public ProducerServiceImpl(ProducerDao producerDao, TransactionManager transactionManager) {
        this.producerDao = producerDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Producer> getAll() {
        Connection connection = transactionManager.getConnection();
        return (List<Producer>) transactionManager.executeAndClose(() -> producerDao.getAllProducers(connection), connection);
    }

}