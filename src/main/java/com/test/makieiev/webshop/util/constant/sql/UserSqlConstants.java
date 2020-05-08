package com.test.makieiev.webshop.util.constant.sql;

public class UserSqlConstants {

    private UserSqlConstants() {
    }

    public static final String INSERT_USER = "INSERT INTO " +
            "users(firstName," +
            "lastName," +
            "login," +
            "email," +
            "password," +
            "link_image," +
            "receiving_newsletter," +
            "Id_role) VALUES(?,?,?,?,?,?,?,?);";

    public static final String UPDATE_USER = "UPDATE users " +
            "SET firstName=?," +
            "lastName=?," +
            "login=?," +
            "email=?," +
            "password=?," +
            "link_image=?," +
            "receiving_newsletter=?," +
            "Id_address=? " +
            "WHERE idUsers=?;";

    public static final String INSERT_ADDRESS = "INSERT INTO " +
            "addresses(Country,City,Street,Floor,Post,HouseNumber) " +
            "VALUES(?,?,?,?,?,?);";

    public static final String DELETE_USER = "DELETE FROM " +
            "users WHERE idUsers=?";

    public static final String SELECT_USER_BY_ID = "SELECT * FROM users " +
            "INNER JOIN addresses ON users.Id_address = addresses.Id_address " +
            "INNER JOIN roles ON users.Id_role = roles.Id_role " +
            "idUsers=?";

    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users " +
            "INNER JOIN addresses ON users.Id_address = addresses.Id_address " +
            "INNER JOIN roles ON users.Id_role = roles.Id_role " +
            "WHERE login=?";

    private static final String SELECT_ALL_ELEMENTS_USER = "SELECT * FROM users ";

    public static final String SELECT_ALL_USERS = SELECT_ALL_ELEMENTS_USER +
            "INNER JOIN addresses ON users.Id_address = addresses.Id_address;";

}
