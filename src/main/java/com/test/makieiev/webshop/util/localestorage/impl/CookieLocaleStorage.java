package com.test.makieiev.webshop.util.localestorage.impl;

import com.test.makieiev.webshop.util.localestorage.LocaleStorage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CookieLocaleStorage implements LocaleStorage {

    private static final String LANGUAGE = "language";
    private static final String TIMEOUT_LOCALE_IN_HOUR = "timeoutLocaleInHour";

    @Override
    public void save(HttpServletRequest request, HttpServletResponse response, String language) {
        long hourTime = Long.parseLong(request.getServletContext().getInitParameter(TIMEOUT_LOCALE_IN_HOUR));
        long secondTime = TimeUnit.HOURS.toSeconds(hourTime);
        Cookie cookie = new Cookie(LANGUAGE, language);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) secondTime);
        response.addCookie(cookie);
    }

    @Override
    public Optional<String> get(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays
                .stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), LANGUAGE))
                .map(Cookie::getName)
                .findFirst();
    }

}