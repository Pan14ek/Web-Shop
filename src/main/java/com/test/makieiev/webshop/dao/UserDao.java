package com.test.makieiev.webshop.dao;

import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.IllegalRemoveException;
import com.test.makieiev.webshop.exception.IllegalUpdateException;
import com.test.makieiev.webshop.exception.NotFoundUserException;
import com.test.makieiev.webshop.exception.NotUniqueUserException;
import com.test.makieiev.webshop.model.user.Address;
import com.test.makieiev.webshop.model.user.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Interface which implemented dao for user
 *
 * @author Oleksii_Makieiev1
 */
public interface UserDao {

    /**
     * This method returns user which searching by id
     *
     * @param id         is parameter which it is use for searching user
     * @param connection is connection with database
     * @return optional user
     * @throws NotFoundUserException if user is not in database
     */
    Optional<User> getUserById(long id, Connection connection);

    /**
     * The method returns user which searching by login
     *
     * @param login      is parameter which it is use for searching user
     * @param connection is connection with database
     * @return optional user
     * @throws NotFoundUserException if user is not in database
     */
    Optional<User> getUserByLogin(String login, Connection connection);

    /**
     * The method add user to database
     *
     * @param user       is user which we should add to database
     * @param connection is connection with database
     * @return true if add successfully or throws exception NotUniqueUserException
     * @throws NotUniqueUserException if user is already in database with this login
     */
    boolean addUser(User user, Connection connection);

    /**
     * The method update user in database
     *
     * @param user       is user which should be updated to the database
     * @param connection is connection with database
     * @return true if updated successfully or throws IllegalUpdateException
     * @throws IllegalUpdateException if user cannot update
     */
    boolean updateUser(User user, Connection connection);

    /**
     * The method removed from database
     *
     * @param id         is parameter which it is use for searching user and remove user with this id
     * @param connection is connection with database
     * @return true if removed successfully or throws IllegalRemoveException
     * @throws IllegalRemoveException if user cannot remove
     */
    boolean removeUser(long id, Connection connection);

    /**
     * The method returns list of users
     *
     * @param connection is connection with database
     * @return list with all users
     * @throws DBException           if errors with database
     * @throws NotFoundUserException if users did not found
     */
    List<User> getAllUsers(Connection connection);

    /**
     * The method add address to database
     *
     * @param address    is address which we should add to database
     * @param connection is connection with database
     * @return true if add successfully or throws exception NotUniqueUserException
     * @throws NotUniqueUserException if user is already in database with this login
     */
    long addAddress(Address address, Connection connection);

}