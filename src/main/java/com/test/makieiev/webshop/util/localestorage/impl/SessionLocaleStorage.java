package com.test.makieiev.webshop.util.localestorage.impl;

import com.test.makieiev.webshop.util.localestorage.LocaleStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionLocaleStorage implements LocaleStorage {

    private static final String LANGUAGE = "language";

    @Override
    public void save(HttpServletRequest request, HttpServletResponse response, String language) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(LANGUAGE, language);
    }

    @Override
    public Optional<String> get(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        String parameter = String.valueOf(httpSession.getAttribute(LANGUAGE));
        return Optional.ofNullable(parameter);
    }

}