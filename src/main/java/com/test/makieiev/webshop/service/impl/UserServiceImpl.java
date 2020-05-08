package com.test.makieiev.webshop.service.impl;

import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.exception.InvalidFieldException;
import com.test.makieiev.webshop.model.user.Address;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final TransactionManager transactionManager;

    public UserServiceImpl(UserDao userDao, TransactionManager transactionManager) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Connection connection = transactionManager.getConnection();
        return (Optional<User>) transactionManager
                .executeAndClose(() -> userDao.getUserByLogin(login, connection), connection);
    }

    @Override
    public void addUser(User user) {
        Connection connection = transactionManager.getConnection();
        String md5Password = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(md5Password);
        transactionManager.doTransaction(() -> userDao.addUser(user, connection), connection);
    }

    @Override
    public void checkPassword(User user, String password) {
        String md5Password = DigestUtils.md5Hex(password);
        if (!Objects.equals(user.getPassword(), md5Password)) {
            throw new InvalidFieldException("Password is not equal!");
        }
    }

    @Override
    public long addAddress(Address address) {
        Connection connection = transactionManager.getConnection();
        return (long) transactionManager.doTransaction(() -> userDao.addAddress(address, connection), connection);
    }

    @Override
    public void updateUser(User user) {
        Connection connection = transactionManager.getConnection();
        transactionManager.doTransaction(() -> userDao.updateUser(user, connection), connection);
    }

}