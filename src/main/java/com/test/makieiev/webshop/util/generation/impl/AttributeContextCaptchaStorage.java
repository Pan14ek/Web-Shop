package com.test.makieiev.webshop.util.generation.impl;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.util.generation.ContextCaptchaStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class AttributeContextCaptchaStorage extends ContextCaptchaStorage {

    public AttributeContextCaptchaStorage(Map<Long, Captcha> captchaValues) {
        super(captchaValues);
    }

    @Override
    public Optional<Captcha> get(HttpServletRequest request) {
        long key = getKey(request);
        return Optional.ofNullable(captchaValues.get(key));
    }

    @Override
    protected long getKey(HttpServletRequest request) {
        return Long.parseLong(String.valueOf(request.getParameter(Captcha.NAME)));
    }

    @Override
    public void put(Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        long captchaKey = getLastKey(captchaValues);
        request.setAttribute(Captcha.NAME, captchaKey);
        captchaValues.put(captchaKey, captcha);
    }

}