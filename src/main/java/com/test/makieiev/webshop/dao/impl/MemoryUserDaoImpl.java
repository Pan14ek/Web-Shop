package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.model.user.Address;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.storage.Storage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

public class MemoryUserDaoImpl implements UserDao {

    private final Storage storage;

    public MemoryUserDaoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Optional<User> getUserById(long id, Connection connection) {
        return Optional.ofNullable(storage.getUsers().get(id));
    }

    @Override
    public Optional<User> getUserByLogin(String login, Connection connection) {
        return storage.getUsers()
                .values()
                .stream()
                .filter(user -> Objects.equals(login, user.getLogin()))
                .findFirst();
    }

    @Override
    public boolean addUser(User user, Connection connection) {
        user.setId(getKey());
        storage.getUsers().put(user.getId(), user);
        return true;
    }


    @Override
    public boolean updateUser(User user, Connection connection) {
        storage.getUsers().replace(user.getId(), user);
        return true;
    }

    @Override
    public boolean removeUser(long id, Connection connection) {
        storage.getUsers().remove(id);
        return true;
    }

    @Override
    public List<User> getAllUsers(Connection connection) {
        return new ArrayList<>(storage.getUsers().values());
    }

    @Override
    public long addAddress(Address address, Connection connection) {
        return address.getId();
    }

    private long getKey() {
        TreeSet<Long> keys = new TreeSet<>(storage.getUsers().keySet());
        return keys.isEmpty() ? 1 : keys.last() + 1;
    }

}