package com.test.makieiev.webshop.util.constant.sql;

public class ItemSqlConstants {

    private ItemSqlConstants() {
    }

    public static final String INSERT_ELECTRONIC = "INSERT INTO " +
            "electronics(ElectronicName," +
            "Price," +
            "idcategory," +
            "Image_link," +
            "idproducer," +
            "Description," +
            "ScreenDiagonal)" +
            "VALUES(?,?,?,?,?,?,?);";

    public static final String UPDATE_ELECTRONIC = "UPDATE users" +
            "SET ElectronicName=?," +
            "Price=?," +
            "idcategory=?," +
            "Image_link=?," +
            "idproducer=?," +
            "Description=?," +
            "ScreenDiagonal=?" +
            "WHERE idElectronic=?;";

    public static final String DELETE_ELECTRONIC = "DELETE FROM electronics WHERE idElectronic=?;";

    public static final String SELECT_ELECTRONIC_JOIN = "SELECT * FROM electronics " +
            "INNER JOIN producers ON electronics.idproducer = producers.idproducers " +
            "INNER JOIN categories ON electronics.idcategory = categories.idcategory ";

    public static final String SELECT_ELECTRONIC_BY_ID = SELECT_ELECTRONIC_JOIN + "WHERE idElectronic=?;";

    public static final String SELECT_ELECTRONIC_BY_TITLE = SELECT_ELECTRONIC_JOIN + "WHERE ElectronicName=?;";

    public static final String SELECT_ALL_ELECTRONICS = SELECT_ELECTRONIC_JOIN + ";";

    public static final String SELECT_AMOUNT_OF_ITEMS = "SELECT COUNT(electronics.idElectronic) as Amount" +
            " FROM electronics ;";

    public static final String SELECT_LIMIT_ELECTRONIC_PAGE = SELECT_ELECTRONIC_JOIN + " LIMIT 6 ";

}