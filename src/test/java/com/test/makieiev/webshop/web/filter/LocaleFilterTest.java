package com.test.makieiev.webshop.web.filter;

import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.localestorage.LocaleStorage;
import com.test.makieiev.webshop.util.localestorage.impl.CookieLocaleStorage;
import com.test.makieiev.webshop.util.localestorage.impl.SessionLocaleStorage;
import com.test.makieiev.webshop.util.wrapper.RequestWrapper;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocaleFilterTest {

    private static final String COOKIE = "cookie";
    private static final String SESSION = "session";
    private static final String DEFAULT_LOCALE = "defaultLocale";
    private static final String LANGUAGE = "language";
    private static final String LOCALE_TYPE = "localeType";
    private static final String RU = "ru";
    private static final String EN = "en";

    private Locale locale;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Mock
    private ServletContext servletContext;

    @Mock
    private WrapperServletFactory wrapperServletFactory;

    @Mock
    private RequestWrapper requestWrapper;

    @Mock
    private FilterChain filterChain;

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private Enumeration<Locale> mockEnumerationLocales;

    @Before
    public void setUp() {
        List<String> locales = new ArrayList<>();
        locales.add(RU);
        locales.add(EN);
        Map<String, LocaleStorage> localeStorageMap = new HashMap<>();
        localeStorageMap.put(COOKIE, new CookieLocaleStorage());
        localeStorageMap.put(SESSION, new SessionLocaleStorage());

        Mockito.when(filterConfig.getInitParameter(LOCALE_TYPE)).thenReturn("session");
        Mockito.when(servletContext.getAttribute(ServletContextConstant.LOCALE_STORAGE)).thenReturn(localeStorageMap);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.LOCALE_LIST)).thenReturn(locales);
        Mockito.doNothing().when(httpSession).setAttribute(LANGUAGE, String.class);
        Mockito.when(request.getSession()).thenReturn(httpSession);
        Mockito.when(request.getServletContext()).thenReturn(servletContext);
        Mockito.when(request.getServletContext().getInitParameter(DEFAULT_LOCALE)).thenReturn(EN);
        Mockito.when(filterConfig.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void shouldChangeLanguageOnUser() throws IOException, ServletException {
        Filter filter = new LocaleFilter();
        locale = new Locale(RU);

        Mockito.when(wrapperServletFactory.getRequestWrapper(request, locale)).thenReturn(requestWrapper);
        Mockito.when(request.getParameter(LANGUAGE)).thenReturn(RU);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY)).thenReturn(wrapperServletFactory);

        filter.init(filterConfig);
        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain, Mockito.times(1)).doFilter(wrapperServletFactory.getRequestWrapper(request, locale), response);
    }

    @Test
    public void shouldChangeOnDefaultLanguageWhenUserChooseIncorrectLanguage() throws ServletException, IOException {
        Filter filter = new LocaleFilter();
        locale = new Locale(EN);

        Mockito.when(wrapperServletFactory.getRequestWrapper(request, locale)).thenReturn(requestWrapper);
        Mockito.when(request.getParameter(LANGUAGE)).thenReturn("rb");
        Mockito.when(servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY)).thenReturn(wrapperServletFactory);

        filter.init(filterConfig);
        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain, Mockito.times(1)).doFilter(wrapperServletFactory.getRequestWrapper(request, locale), response);
    }

    @Test
    public void shouldSetDefaultLanguage() throws IOException, ServletException {
        Filter filter = new LocaleFilter();
        locale = new Locale(EN);

        Mockito.when(request.getLocales()).thenReturn(mockEnumerationLocales);
        Mockito.when(wrapperServletFactory.getRequestWrapper(request, locale)).thenReturn(requestWrapper);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY)).thenReturn(wrapperServletFactory);

        filter.init(filterConfig);
        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain, Mockito.times(1)).doFilter(wrapperServletFactory.getRequestWrapper(request, locale), response);
    }

    @Test
    public void shouldSetBrowserLanguage() throws IOException, ServletException {
        Filter filter = new LocaleFilter();
        locale = new Locale(RU);

        Mockito.when(mockEnumerationLocales.nextElement()).thenReturn(locale);
        Mockito.when(mockEnumerationLocales.hasMoreElements()).thenReturn(true);
        Mockito.when(request.getLocales()).thenReturn(mockEnumerationLocales);
        Mockito.when(wrapperServletFactory.getRequestWrapper(request, locale)).thenReturn(requestWrapper);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.WRAPPER_SERVLET_FACTORY)).thenReturn(wrapperServletFactory);

        filter.init(filterConfig);
        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain, Mockito.times(1)).doFilter(wrapperServletFactory.getRequestWrapper(request, locale), response);
    }

}