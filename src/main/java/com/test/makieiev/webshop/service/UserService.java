package com.test.makieiev.webshop.service;

import com.test.makieiev.webshop.model.user.Address;
import com.test.makieiev.webshop.model.user.User;

import java.util.Optional;

/**
 * The service is responsible for operating with user data
 *
 * @author Oleksii_Makieiev1
 */
public interface UserService {

    /**
     * The method returns optional user
     *
     * @param login is user's login
     * @return optional user
     */
    Optional<User> getByLogin(String login);

    /**
     * The method adds user to database
     *
     * @param user is the converted and valid user from dto
     */
    void addUser(User user);

    /**
     * The method check user password with request password
     *
     * @param user     is user from database
     * @param password is password from request
     */
    void checkPassword(User user, String password);

    /**
     * The method adds address to database
     *
     * @param address is the converted and valid address from dto
     */
    long addAddress(Address address);

    void updateUser(User user);
}