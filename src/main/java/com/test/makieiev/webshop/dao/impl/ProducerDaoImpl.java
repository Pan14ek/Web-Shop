package com.test.makieiev.webshop.dao.impl;

import com.test.makieiev.webshop.dao.ProducerDao;
import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.NotFoundProducerException;
import com.test.makieiev.webshop.model.item.Producer;
import com.test.makieiev.webshop.util.constant.sql.ProducerSqlConstants;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducerDaoImpl implements ProducerDao {

    private static final String ID_PRODUCERS = "idProducers";
    private static final String CITY = "City";
    private static final String COUNTRY = "Country";
    private static final String TITLE = "Title";

    private static final Logger LOGGER = Logger.getLogger(ProducerDaoImpl.class);

    @Override
    public List<Producer> getAllProducers(Connection connection) {
        try {
            List<Producer> producers = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(ProducerSqlConstants.SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            checkProducerResultSet(resultSet, producers);
            while (resultSet.next()) {
                producers.add(getProducerFromResultSet(resultSet));
            }
            return producers;
        } catch (SQLException e) {
            LOGGER.error("Error with database", e);
            throw new DBException("Error with database", e);
        }
    }

    private void checkProducerResultSet(ResultSet userResultSet, List<Producer> producers) throws SQLException {
        if (!userResultSet.next()) {
            throw new NotFoundProducerException("Producers did not found");
        }
        producers.add(getProducerFromResultSet(userResultSet));
    }

    private Producer getProducerFromResultSet(ResultSet resultSet) throws SQLException {
        Producer producer = new Producer();
        producer.setId(resultSet.getLong(ID_PRODUCERS));
        producer.setCity(resultSet.getString(CITY));
        producer.setCountry(resultSet.getString(COUNTRY));
        producer.setTitle(resultSet.getString(TITLE));
        return producer;
    }

}