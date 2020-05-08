package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserDaoImplTest {

    private UserDao memoryUserDao;
    private Map<Long, User> users;

    @Before
    public void init() {
        users = initUsers();
        Storage storage = new Storage(users);
        memoryUserDao = new MemoryUserDaoImpl(storage);
    }

    @Test
    public void shouldGetUserByLogin() {
        Optional<User> user = memoryUserDao.getUserByLogin("one", null);

        Assert.assertEquals("one", user.get().getLogin());
    }

    @Test
    public void shouldGetUserById() {
        Optional<User> user = memoryUserDao.getUserById(1, null);

        Assert.assertEquals("one", user.get().getLogin());
    }

    @Test
    public void shouldAddUser() {
        User user = new User(9L, "nine", "nine", "nine", "nine@gmail.com", "1234567", true, "img", new Role(1, "RegisteredUser"));

        memoryUserDao.addUser(user, null);

        Assert.assertEquals(7, users.size());
    }

    @Test
    public void shouldUpdateUser() {
        User actual = new User(5L, "firstName", "five", "five", "five@gmail.com", "1234567", true, "img", new Role(1, "RegisteredUser"));

        memoryUserDao.updateUser(actual, null);

        Optional<User> expected = memoryUserDao.getUserById(5L, null);

        Assert.assertEquals(actual.getFirstName(), expected.get().getFirstName());
    }

    @Test
    public void shouldRemoveUser() {
        memoryUserDao.removeUser(1L, null);

        Assert.assertEquals(5L, users.size());
    }

    @Test
    public void shouldGetAllUsers() {
        List<User> usersFromDao = memoryUserDao.getAllUsers(null);

        Assert.assertEquals(6, usersFromDao.size());
    }

    private Map<Long, User> initUsers() {
        Map<Long, User> users = new HashMap<>();
        Role role = new Role(1, "RegisteredUser");
        users.put(1L, new User(1L, "one", "one", "one", "one@gmail.com", "1234567", true, "img", role));
        users.put(2L, new User(2L, "two", "two", "two", "two@gmail.com", "1234567", false, "img", role));
        users.put(3L, new User(3L, "three", "three", "three", "three@gmail.com", "1234567", true, "img", role));
        users.put(4L, new User(4L, "four", "four", "four", "four@gmail.com", "1234567", true, "img", role));
        users.put(5L, new User(5L, "five", "five", "five", "five@gmail.com", "1234567", true, "img", role));
        users.put(6L, new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "img", role));
        return users;
    }

}