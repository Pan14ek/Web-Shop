package com.test.makieiev.webshop.web.filter;

import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.wrapper.GZIPHttpServletResponseWrapper;
import com.test.makieiev.webshop.util.wrapper.WrapperServletFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ResponseFilterTest {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private FilterChain filterChain;

    @Mock
    private WrapperServletFactory wrapperServletFactory;

    @Mock
    private GZIPHttpServletResponseWrapper gzipHttpServletResponseWrapper;

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private ServletContext servletContext;

    @Before
    public void setUp() {
        Mockito.when(filterConfig.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void shouldReturnDefaultResponse() throws IOException, ServletException {
        Filter filter = new ResponseFilter();

        Mockito.when(wrapperServletFactory.getGZIPHttpServletResponseWrapper(response)).thenReturn(gzipHttpServletResponseWrapper);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY)).thenReturn(wrapperServletFactory);
        Mockito.when(request.getHeader("accept-encoding")).thenReturn("test");
        filter.init(filterConfig);
        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldReturnWrapperResponse() throws IOException, ServletException {
        Filter filter = new ResponseFilter();

        Mockito.when(wrapperServletFactory.getGZIPHttpServletResponseWrapper(response)).thenReturn(gzipHttpServletResponseWrapper);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY)).thenReturn(wrapperServletFactory);
        Mockito.when(request.getHeader("accept-encoding")).thenReturn("gzip");
        filter.init(filterConfig);
        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, gzipHttpServletResponseWrapper);
    }

}