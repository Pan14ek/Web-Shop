package com.test.makieiev.webshop.web.servlet;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.generation.CaptchaStorage;
import com.test.makieiev.webshop.util.generation.impl.SessionCaptchaStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GenerateCaptchaServletTest {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    @Mock
    private ServletContext servletContext;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Mock
    private ServletOutputStream out;

    @Test
    public void shouldGetImage() throws ServletException, IOException {
        GenerateCaptchaServlet generateCaptchaServlet = new GenerateCaptchaServlet();
        CaptchaStorage captchaStorage = new SessionCaptchaStorage(new HashMap<>());
        Captcha captcha = new Captcha.Builder(WIDTH, HEIGHT)
                .addText()
                .addBorder()
                .gimp()
                .addBackground(new GradiatedBackgroundProducer())
                .build();

        Mockito.when(httpSession.getAttribute(Captcha.NAME)).thenReturn(captcha);
        Mockito.when(response.getOutputStream()).thenReturn(out);
        Mockito.when(request.getSession()).thenReturn(httpSession);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn(captchaStorage);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);

        generateCaptchaServlet.init(servletConfig);
        generateCaptchaServlet.doGet(request, response);

        Mockito.verify(response).setContentType("image/png");
    }

    @Test
    public void shouldRedirectToIndexPage() throws ServletException, IOException {
        GenerateCaptchaServlet generateCaptchaServlet = new GenerateCaptchaServlet();
        CaptchaStorage captchaStorage = new SessionCaptchaStorage(new HashMap<>());

        Mockito.when(request.getSession()).thenReturn(httpSession);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn(captchaStorage);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);

        generateCaptchaServlet.init(servletConfig);
        generateCaptchaServlet.doGet(request, response);

        Mockito.verify(response, Mockito.times(1)).sendRedirect(Link.SIGN_UP);
    }

}