package com.test.makieiev.webshop.util.generation.impl;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.util.generation.CaptchaStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class SessionCaptchaStorage extends CaptchaStorage {

    public SessionCaptchaStorage(Map<Long, Captcha> captchaValues) {
        super(captchaValues);
    }

    @Override
    public Optional<Captcha> get(HttpServletRequest request) {
        return Optional.ofNullable((Captcha) request.getSession().getAttribute(Captcha.NAME));
    }

    @Override
    public void put(Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Captcha.NAME, captcha);
    }

}