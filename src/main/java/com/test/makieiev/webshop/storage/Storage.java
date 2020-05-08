package com.test.makieiev.webshop.storage;

import com.test.makieiev.webshop.model.user.User;

import java.util.Map;

public class Storage {

    private final Map<Long, User> users;

    public Storage(Map<Long, User> users) {
        this.users = users;
    }

    public Map<Long, User> getUsers() {
        return users;
    }

}