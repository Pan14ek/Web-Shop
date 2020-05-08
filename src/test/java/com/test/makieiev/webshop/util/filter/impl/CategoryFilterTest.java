package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.filter.Filter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterTest {

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
    public void shouldGenerateCategoryQuery() {
        String expected = "WHERE categories.CategoryName IN ('One','Two')";
        List<String> categories = new ArrayList<>();
        Filter categoryFilter = new CategoryFilter();

        categories.add("One");
        categories.add("Two");
        productFilterDto.setCategories(categories);
        String actual = categoryFilter.doFilter(query, productFilterDto).toString();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGenerateCategoryProducerAmountItemPageQuery() {
        String expected = "WHERE categories.CategoryName IN ('One','Two') AND producers.Title IN ('First','Second') LIMIT 3 OFFSET 3";
        List<String> categories = new ArrayList<>();
        List<String> producers = new ArrayList<>();
        Filter producerFilter = new ProducerFilter();
        Filter categoryFilter = new CategoryFilter();
        Filter amountItemFilter = new AmountItemFilter();
        Filter pageFiler = new PageFilter();

        categoryFilter.setNext(producerFilter);
        producerFilter.setNext(amountItemFilter);
        amountItemFilter.setNext(pageFiler);
        producers.add("First");
        producers.add("Second");
        categories.add("One");
        categories.add("Two");
        productFilterDto.setPage(2);
        productFilterDto.setAmountItem(3);
        productFilterDto.setProducers(producers);
        productFilterDto.setCategories(categories);
        String actual = categoryFilter.doFilter(query, productFilterDto).toString();

        Assert.assertEquals(expected, actual);
    }

}