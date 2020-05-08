package com.test.makieiev.webshop.util.content.impl;

import com.test.makieiev.webshop.util.content.ContentInstallation;

import javax.servlet.http.HttpServletResponse;

public class PngContentInstallation implements ContentInstallation {

    @Override
    public void install(HttpServletResponse response) {
        response.setHeader("Cache-Control", "private,no-cache,no-store");
        response.setContentType("image/png");
    }

}
