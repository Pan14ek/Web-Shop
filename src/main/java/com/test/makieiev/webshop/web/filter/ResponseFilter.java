package com.test.makieiev.webshop.web.filter;

import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.wrapper.GZIPHttpServletResponseWrapper;
import com.test.makieiev.webshop.util.wrapper.WrapperServletFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseFilter implements Filter {

    private WrapperServletFactory wrapperServletFactory;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        wrapperServletFactory = (WrapperServletFactory) servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String header = httpServletRequest.getHeader("accept-encoding");
        if (header.contains("gzip")) {
            GZIPHttpServletResponseWrapper wrappedResponse = wrapperServletFactory.getGZIPHttpServletResponseWrapper(httpServletResponse);
            chain.doFilter(request, wrappedResponse);
            wrappedResponse.finishResponse();
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}