package com.test.makieiev.webshop.util.localestorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface LocaleStorage {

    void save(HttpServletRequest request, HttpServletResponse response, String language);

    Optional<String> get(HttpServletRequest request);

}