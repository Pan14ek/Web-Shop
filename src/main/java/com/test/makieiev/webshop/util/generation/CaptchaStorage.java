package com.test.makieiev.webshop.util.generation;

import cn.apiclub.captcha.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public abstract class CaptchaStorage {

    protected Map<Long, Captcha> captchaValues;

    public CaptchaStorage(Map<Long, Captcha> captchaValues) {
        this.captchaValues = captchaValues;
    }

    public abstract Optional<Captcha> get(HttpServletRequest request);

    public abstract void put(Captcha captcha, HttpServletRequest request, HttpServletResponse response);

}