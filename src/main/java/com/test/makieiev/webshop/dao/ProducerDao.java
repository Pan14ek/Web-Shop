package com.test.makieiev.webshop.dao;


import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.NotFoundProducerException;
import com.test.makieiev.webshop.model.item.Producer;

import java.sql.Connection;
import java.util.List;

/**
 * Interface which implemented dao for producer
 *
 * @author Oleksii_Makieiev1
 */
public interface ProducerDao {

    /**
     * The methods returns list of producers
     *
     * @param connection is connection with database
     * @return list with all categories
     * @throws DBException               if errors with database
     * @throws NotFoundProducerException if producer did not found
     */
    List<Producer> getAllProducers(Connection connection);

}