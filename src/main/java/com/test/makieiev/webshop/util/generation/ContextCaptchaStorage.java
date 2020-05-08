package com.test.makieiev.webshop.util.generation;

import cn.apiclub.captcha.Captcha;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeSet;

public abstract class ContextCaptchaStorage extends CaptchaStorage {

    public ContextCaptchaStorage(Map<Long, Captcha> captchaValues) {
        super(captchaValues);
    }

    protected abstract long getKey(HttpServletRequest request);

    protected long getLastKey(Map<Long, Captcha> captchaValues) {
        TreeSet<Long> keys = new TreeSet<>(captchaValues.keySet());
        return keys.isEmpty() ? 1 : keys.last() + 1;
    }

}