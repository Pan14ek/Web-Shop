package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.filter.Filter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PageFilterTest {

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
    public void shouldGeneratePageQuery() {
        String expected = " LIMIT 3 OFFSET 3";
        Filter amountItemFilter = new AmountItemFilter();
        Filter pageFiler = new PageFilter();

        amountItemFilter.setNext(pageFiler);
        productFilterDto.setAmountItem(3);
        productFilterDto.setPage(2);
        String actual = amountItemFilter.doFilter(query, productFilterDto).toString();

        Assert.assertEquals(expected, actual);
    }

}