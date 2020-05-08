package com.test.makieiev.webshop.service.impl;

import com.test.makieiev.webshop.dao.CategoryDao;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.model.item.Category;
import com.test.makieiev.webshop.service.CategoryService;

import java.sql.Connection;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final TransactionManager transactionManager;

    public CategoryServiceImpl(CategoryDao categoryDao, TransactionManager transactionManager) {
        this.categoryDao = categoryDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Category> getAll() {
        Connection connection = transactionManager.getConnection();
        return (List<Category>) transactionManager
                .executeAndClose(() -> categoryDao.getAllCategories(connection), connection);
    }

}