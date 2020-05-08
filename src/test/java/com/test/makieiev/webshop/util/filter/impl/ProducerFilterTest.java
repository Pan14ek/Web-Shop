package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.filter.Filter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProducerFilterTest {

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
    public void shouldGenerateProducerQuery() {
        String expected = "WHERE producers.Title IN ('First','Second')";
        List<String> producers = new ArrayList<>();
        Filter producerFilter = new ProducerFilter();

        producers.add("First");
        producers.add("Second");
        productFilterDto.setProducers(producers);

        String actual = producerFilter.doFilter(query, productFilterDto).toString();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateProducerAndCategoryQuery() {
        String expected = "WHERE categories.CategoryName IN ('One','Two') AND producers.Title IN ('First','Second')";
        List<String> producers = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        Filter producerFilter = new ProducerFilter();
        Filter categoryFilter = new CategoryFilter();

        producers.add("First");
        producers.add("Second");
        categories.add("One");
        categories.add("Two");
        productFilterDto.setProducers(producers);
        productFilterDto.setCategories(categories);
        categoryFilter.setNext(producerFilter);
        String actual = categoryFilter.doFilter(query, productFilterDto).toString();

        Assert.assertEquals(expected, actual);
    }

}