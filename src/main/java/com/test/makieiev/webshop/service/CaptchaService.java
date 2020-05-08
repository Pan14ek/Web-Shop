package com.test.makieiev.webshop.service;

import cn.apiclub.captcha.Captcha;

/**
 * The service is responsible for operating with captcha
 *
 * @author Oleksii_Makieiev1
 */
public interface CaptchaService {

    /**
     * The method generate captcha
     *
     * @return random captcha
     */
    Captcha create();

}