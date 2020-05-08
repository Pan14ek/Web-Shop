package com.test.makieiev.webshop.service;

import com.test.makieiev.webshop.model.item.Producer;

import java.util.List;

/**
 * The service is responsible for operating with producer data
 *
 * @author Oleksii_Makieiev1
 */
public interface ProducerService {

    /**
     * The method returns list of producers
     *
     * @return list of producers
     */
    List<Producer> getAll();

}