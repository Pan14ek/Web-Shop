package com.test.makieiev.webshop.service.impl;

import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.exception.InvalidFieldException;
import com.test.makieiev.webshop.exception.NotFoundUserException;
import com.test.makieiev.webshop.exception.NotUniqueUserException;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private Connection connection;

    @Mock
    private DataSource dataSource;

    @Before
    public void init() throws SQLException {
        TransactionManager transactionManager = new TransactionManager(dataSource);

        Mockito.when(dataSource.getConnection()).thenReturn(connection);

        userService = new UserServiceImpl(userDao, transactionManager);
    }

    @Test
    public void shouldReturnUserWhenUseMethodGetByLogin() {
        User user = new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "img", new Role(1, "RegisteredUser"));
        Optional<User> actualOptionalUser = Optional.of(user);

        Mockito.when(userDao.getUserByLogin("six", connection)).thenReturn(actualOptionalUser);

        Optional<User> expectedOptionalUser = userService.getByLogin("six");

        Assert.assertEquals(actualOptionalUser, expectedOptionalUser);
    }

    @Test(expected = NotFoundUserException.class)
    public void shouldThrowExceptionWhenUseMethodGetByLogin() {
        Mockito.when(userDao.getUserByLogin("six", connection)).thenThrow(NotFoundUserException.class);

        userService.getByLogin("six");
    }

    @Test(expected = InvalidFieldException.class)
    public void shouldThrowExceptionWhenCheckPassword() {
        User user = new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "img", new Role(1, "RegisteredUser"));

        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        String password = "password";

        userService.checkPassword(user, password);
    }

    @Test(expected = NotUniqueUserException.class)
    public void shouldThrowExceptionWhenAddUser() {
        User user = new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "img", new Role(1, "RegisteredUser"));

        Mockito.when(userDao.addUser(user, connection)).thenThrow(NotUniqueUserException.class);

        userService.addUser(user);
    }

}