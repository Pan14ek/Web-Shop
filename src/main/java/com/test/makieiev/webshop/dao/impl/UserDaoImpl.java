package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.IllegalRemoveException;
import com.test.makieiev.webshop.exception.IllegalUpdateException;
import com.test.makieiev.webshop.exception.NotFoundUserException;
import com.test.makieiev.webshop.exception.NotKeyException;
import com.test.makieiev.webshop.exception.NotUniqueUserException;
import com.test.makieiev.webshop.model.user.Address;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.constant.sql.UserSqlConstants;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public Optional<User> getUserById(long id, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UserSqlConstants.SELECT_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet userResultSet = preparedStatement.executeQuery();
            return Optional.of(getUserFromResultSet(userResultSet));
        } catch (SQLException e) {
            LOGGER.error("User did not found by id", e);
            throw new NotFoundUserException("User did not found by id", e);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UserSqlConstants.SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet userResultSet = preparedStatement.executeQuery();
            Optional<User> optionalUser = Optional.empty();
            while (userResultSet.next()) {
                optionalUser = Optional.of(getUserFromResultSet(userResultSet));
            }
            return optionalUser;
        } catch (SQLException e) {
            LOGGER.error("User did not found by login", e);
            throw new NotFoundUserException("User did not found by login", e);
        }
    }

    @Override
    public boolean addUser(User user, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UserSqlConstants.INSERT_USER);
            int index = 1;
            preparedStatement.setString(index++, user.getFirstName());
            preparedStatement.setString(index++, user.getLastName());
            preparedStatement.setString(index++, user.getLogin());
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index++, user.getPassword());
            preparedStatement.setString(index++, user.getImageLink());
            preparedStatement.setBoolean(index++, user.isReceivingNewsletter());
            preparedStatement.setLong(index, user.getRole().getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("User is already in database with this login", e);
            throw new NotUniqueUserException("User is already in database with this login", e);
        }
    }

    @Override
    public boolean updateUser(User user, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UserSqlConstants.UPDATE_USER);
            int index = 1;
            preparedStatement.setString(index++, user.getFirstName());
            preparedStatement.setString(index++, user.getLastName());
            preparedStatement.setString(index++, user.getLogin());
            preparedStatement.setString(index++, user.getEmail());
            preparedStatement.setString(index++, user.getPassword());
            preparedStatement.setString(index++, user.getImageLink());
            preparedStatement.setBoolean(index++, user.isReceivingNewsletter());
            preparedStatement.setLong(index++, user.getAddress().getId());
            preparedStatement.setLong(index, user.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("User cannot update", e);
            throw new IllegalUpdateException("User cannot update", e);
        }
    }

    @Override
    public boolean removeUser(long id, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UserSqlConstants.DELETE_USER);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error("User cannot remove", e);
            throw new IllegalRemoveException("User cannot remove", e);
        }
    }

    @Override
    public List<User> getAllUsers(Connection connection) {
        try {
            List<User> users = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(UserSqlConstants.SELECT_ALL_USERS);
            ResultSet userResultSet = preparedStatement.executeQuery();
            checkUserResultSet(userResultSet);
            while (userResultSet.next()) {
                users.add(getUserFromResultSet(userResultSet));
            }
            return users;
        } catch (SQLException e) {
            LOGGER.error("Error with database", e);
            throw new DBException("Error with database", e);
        }
    }

    @Override
    public long addAddress(Address address, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UserSqlConstants.INSERT_ADDRESS, PreparedStatement.RETURN_GENERATED_KEYS);
            int index = 1;
            preparedStatement.setString(index++, address.getCountry());
            preparedStatement.setString(index++, address.getCity());
            preparedStatement.setString(index++, address.getStreet());
            preparedStatement.setInt(index++, address.getFloor());
            preparedStatement.setInt(index++, address.getPost());
            preparedStatement.setInt(index, address.getHouseNumber());
            preparedStatement.executeUpdate();
            ResultSet keyResultSet = preparedStatement.getGeneratedKeys();
            checkKeyResultSet(keyResultSet);
            return keyResultSet.getLong(1);
        } catch (SQLException e) {
            LOGGER.error("Error with database", e);
            throw new DBException("Error with database", e);
        }
    }

    private void checkKeyResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new NotKeyException("Element did not add to database");
        }
    }

    private void checkUserResultSet(ResultSet userResultSet) throws SQLException {
        if (!userResultSet.next()) {
            throw new NotFoundUserException("Users did not found");
        }
    }

    private User getUserFromResultSet(ResultSet userResultSet) throws SQLException {
        User user = new User();
        Role role = new Role();
        Address address = new Address();
        role.setId(userResultSet.getLong("Id_role"));
        role.setTitle(userResultSet.getString("Title"));
        user.setId(userResultSet.getLong(UserConstant.ID));
        user.setFirstName(userResultSet.getString(UserConstant.FIRST_NAME));
        user.setLastName(userResultSet.getString(UserConstant.LAST_NAME));
        user.setLogin(userResultSet.getString(UserConstant.LOGIN));
        user.setEmail(userResultSet.getString(UserConstant.EMAIL));
        user.setReceivingNewsletter(userResultSet.getBoolean(UserConstant.RECEIVING_NEWSLETTER));
        user.setImageLink(userResultSet.getString(UserConstant.IMAGE_LINK));
        user.setPassword(userResultSet.getString(UserConstant.PASSWORD));
        user.setRole(role);
        address.setId(userResultSet.getLong(UserConstant.ID_ADDRESS));
        address.setCountry(userResultSet.getString(UserConstant.COUNTRY));
        address.setStreet(userResultSet.getString(UserConstant.STREET));
        address.setCity(userResultSet.getString(UserConstant.CITY));
        address.setFloor(userResultSet.getInt(UserConstant.FLOOR));
        address.setPost(userResultSet.getInt(UserConstant.POST));
        address.setHouseNumber(userResultSet.getInt(UserConstant.HOUSE_NUMBER));
        user.setAddress(address);
        return user;
    }

}