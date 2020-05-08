package com.test.makieiev.webshop.service.impl;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import com.test.makieiev.webshop.service.CaptchaService;

public class CaptchaServiceImpl implements CaptchaService {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    @Override
    public Captcha create() {
        return new Captcha.Builder(WIDTH, HEIGHT)
                .addText()
                .addBorder()
                .gimp()
                .addBackground(new GradiatedBackgroundProducer())
                .build();
    }

}