package com.test.makieiev.webshop.web.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class HeaderFilterTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private FilterChain filterChain;

    @Test
    public void shouldSetNoCacheHeader() throws IOException, ServletException {
        Filter filter = new HeaderFilter();

        filter.doFilter(request, response, filterChain);

        Mockito.verify(response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    }

}