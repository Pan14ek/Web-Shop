package com.test.makieiev.webshop.web.tag;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.util.constant.Link;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Objects;

public class CaptchaTag extends SimpleTagSupport {

    private static final String IMG_SRC = "<img class='col-sm-2 col-form-label' src='";
    private static final String ALT_CAPTCHA = "' alt='captcha' />";
    private static final String INPUT_TYPE_TEXT_HIDDEN_ID_SIMPLE_CAPTCHA_NAME = "<input type = 'hidden' class='col-sm-2 col-form-label' hidden id = 'simpleCaptcha' name='";
    private static final String VALUE = "' value = '";
    private static final String END_INPUT = "'>";
    private static final String EQUAL_MARK = "=";
    private static final String QUESTION_MARK = "?";

    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        printCaptcha(request);
    }

    private void printCaptcha(HttpServletRequest request) throws IOException {
        JspWriter jspWriter = getJspContext().getOut();
        if (Objects.nonNull(request.getAttribute(Captcha.NAME))) {
            printCaptchaWithHiddenField(jspWriter, request);
        } else {
            jspWriter.print(IMG_SRC + Link.CAPTCHA + ALT_CAPTCHA);
        }
    }

    private void printCaptchaWithHiddenField(JspWriter jspWriter, HttpServletRequest request) throws IOException {
        jspWriter.print(INPUT_TYPE_TEXT_HIDDEN_ID_SIMPLE_CAPTCHA_NAME +
                Captcha.NAME + VALUE + request.getAttribute(Captcha.NAME) + END_INPUT);
        jspWriter.print(IMG_SRC + Link.CAPTCHA + QUESTION_MARK +
                Captcha.NAME + EQUAL_MARK + request.getAttribute(Captcha.NAME) + ALT_CAPTCHA);
    }

}