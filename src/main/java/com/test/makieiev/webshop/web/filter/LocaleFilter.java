package com.test.makieiev.webshop.web.filter;

import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.localestorage.LocaleStorage;
import com.test.makieiev.webshop.util.wrapper.RequestWrapper;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class LocaleFilter implements Filter {

    private static final String LOCALE_TYPE = "localeType";
    private static final String DEFAULT_LOCALE = "defaultLocale";
    private static final String LANGUAGE = "language";

    private LocaleStorage localeStorage;
    private List<String> localeList;
    private WrapperServletFactory wrapperServletFactory;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        String localeType = filterConfig.getInitParameter(LOCALE_TYPE);
        Map<String, LocaleStorage> localeStorageMap = (Map<String, LocaleStorage>) servletContext.getAttribute(ServletContextConstant.LOCALE_STORAGE);
        localeList = (List<String>) servletContext.getAttribute(ServletContextConstant.LOCALE_LIST);
        localeStorage = localeStorageMap.get(localeType);
        wrapperServletFactory = (WrapperServletFactory) servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Optional<String> language = Optional.ofNullable(httpServletRequest.getParameter(LANGUAGE));
        language = checkLanguageInformationFromStorage(httpServletRequest, language);
        Locale locale = operateLocale(httpServletRequest, httpServletResponse, language);
        RequestWrapper requestWrapper = wrapperServletFactory.getRequestWrapper(httpServletRequest, locale);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
    }

    private Optional<String> checkLanguageInformationFromStorage(HttpServletRequest httpServletRequest, Optional<String> language) {
        if (!language.isPresent()) {
            language = localeStorage.get(httpServletRequest);
            language = checkStorageLanguageParameter(httpServletRequest, language);
        }
        return language;
    }


    private Locale operateLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Optional<String> language) {
        Locale locale;
        if (language.isPresent() && localeList.contains(language.get())) {
            localeStorage.save(httpServletRequest, httpServletResponse, language.get());
            locale = new Locale(language.get());
        } else {
            String defaultLocale = httpServletRequest.getServletContext().getInitParameter(DEFAULT_LOCALE);
            localeStorage.save(httpServletRequest, httpServletResponse, defaultLocale);
            locale = new Locale(defaultLocale);
        }
        return locale;
    }

    private Optional<String> checkStorageLanguageParameter(HttpServletRequest httpServletRequest, Optional<String> language) {
        if (language.isPresent()) {
            language = getLocale(httpServletRequest);
        }
        return language;
    }

    private Optional<String> getLocale(HttpServletRequest request) {
        Enumeration locales = request.getLocales();
        while (locales.hasMoreElements()) {
            Locale locale = (Locale) locales.nextElement();
            if (checkLocaleLanguage(locale.getLanguage())) {
                return Optional.of(locale.getLanguage());
            }
        }
        return Optional.empty();
    }

    private boolean checkLocaleLanguage(String browserLocale) {
        return localeList.contains(browserLocale);
    }

}