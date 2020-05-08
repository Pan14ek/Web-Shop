package com.test.makieiev.webshop.util.generation.impl;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.util.generation.ContextCaptchaStorage;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CookieContextCaptchaStorage extends ContextCaptchaStorage {

    public CookieContextCaptchaStorage(Map<Long, Captcha> captchaValues) {
        super(captchaValues);
    }

    @Override
    public Optional<Captcha> get(HttpServletRequest request) {
        long key = getKey(request);
        return Optional.ofNullable(captchaValues.get(key));
    }

    @Override
    protected long getKey(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), Captcha.NAME))
                .findFirst()
                .map(Cookie::getValue)
                .map(Long::parseLong)
                .orElse(0L);
    }

    @Override
    public void put(Captcha captcha, HttpServletRequest request, HttpServletResponse response) {
        long captchaKey = getLastKey(captchaValues);
        Cookie captchaCookie = new Cookie(Captcha.NAME, captchaKey + StringUtils.EMPTY);
        captchaCookie.setHttpOnly(true);
        response.addCookie(captchaCookie);
        captchaValues.put(captchaKey, captcha);
    }

}