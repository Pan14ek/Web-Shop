package com.test.makieiev.webshop.util.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class WrapperServletFactory {

    public RequestWrapper getRequestWrapper(HttpServletRequest request, Locale locale) {
        return new RequestWrapper(request, locale);
    }

    public GZIPHttpServletResponseWrapper getGZIPHttpServletResponseWrapper(HttpServletResponse response) {
        return new GZIPHttpServletResponseWrapper(response);
    }

}