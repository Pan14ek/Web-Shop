package com.test.makieiev.webshop.util.constant.sql;

public class OrderSqlConstants {

    private OrderSqlConstants() {
    }

    public static final String INSERT_ORDER_ITEM = "INSERT INTO " +
            "orders(idUser," +
            "Status," +
            "State," +
            "DateTime) " +
            "VALUES(?,?,?,?);";

    public static final String INSERT_ORDER_DETAILS = "INSERT INTO " +
            "orderdetails(idElectronic,Amount,Price,idOrder) " +
            "VALUES(?,?,?,?)";

    public static final String SELECT_ORDER_BY_USER = "SELECT * " +
            "FROM orderdetails WHERE idElectronic=?;";

    public static final String SELECT_ORDER_ITEM_BY_ORDER_ID = "select * from orderdetails " +
            "INNER JOIN electronics ON electronics.idElectronic = orderdetails.idElectronic " +
            "WHERE idOrder=?;";

}