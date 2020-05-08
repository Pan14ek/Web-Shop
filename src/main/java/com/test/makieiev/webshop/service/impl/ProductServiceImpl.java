package com.test.makieiev.webshop.service.impl;

import com.test.makieiev.webshop.dao.ProductDao;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.model.item.Product;
import com.test.makieiev.webshop.service.ProductService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final TransactionManager transactionManager;

    public ProductServiceImpl(ProductDao productDao, TransactionManager transactionManager) {
        this.productDao = productDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<Product> getById(long id) {
        Connection connection = transactionManager.getConnection();
        return (Optional<Product>) transactionManager.executeAndClose(() -> productDao.getProductById(id, connection), connection);
    }

    @Override
    public List<Product> getAll() {
        Connection connection = transactionManager.getConnection();
        return (List<Product>) transactionManager
                .executeAndClose(() -> productDao.getAllProducts(connection), connection);
    }

    @Override
    public List<Product> getAllSortedAndFiltered(String query) {
        Connection connection = transactionManager.getConnection();
        return (List<Product>) transactionManager
                .executeAndClose(() -> productDao.getFilteredAndSortedProducts(query, connection), connection);
    }

    @Override
    public long getAmount() {
        Connection connection = transactionManager.getConnection();
        return (long) transactionManager
                .executeAndClose(() -> productDao.getProductsAmount(connection), connection);
    }

}