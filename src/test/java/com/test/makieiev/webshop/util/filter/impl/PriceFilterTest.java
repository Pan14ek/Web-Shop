package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.filter.Filter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PriceFilterTest {

    private ProductFilterDto productFilterDto;
    private StringBuilder query;

    @Before
    public void init() {
        productFilterDto = new ProductFilterDto();
        query = new StringBuilder();
    }

    @After
    public void clean() {
        query.delete(0, query.length());
    }

    @Test
    public void shouldGeneratePriceQuery() {
        String expected = "WHERE electronics.Price BETWEEN 1.0 AND 2.0";
        List<Double> prices = new ArrayList<>();
        Filter priceFilter = new PriceFilter();

        prices.add(1.0);
        prices.add(2.0);
        productFilterDto.setPrice(prices);
        String actual = priceFilter.doFilter(query, productFilterDto).toString();

        Assert.assertEquals(expected, actual);
    }

}