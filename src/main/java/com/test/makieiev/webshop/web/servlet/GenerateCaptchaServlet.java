package com.test.makieiev.webshop.web.servlet;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.generation.CaptchaStorage;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet(Link.CAPTCHA)
public class GenerateCaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = -5520496565996767868L;

    private static final Logger logger = Logger.getLogger(GenerateCaptchaServlet.class);

    private CaptchaStorage captchaStorage;

    @Override
    public void init() {
        ServletContext servletContext = getServletConfig().getServletContext();
        captchaStorage = (CaptchaStorage) servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (captchaStorage.get(request).isPresent()) {
                Captcha captcha = captchaStorage.get(request).get();
                sendImage(captcha, response);
            } else {
                response.sendRedirect(Link.SIGN_UP);
            }
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    private void sendImage(Captcha captcha, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "private,no-cache,no-store");
        response.setContentType("image/png");
        ServletOutputStream out = response.getOutputStream();
        BufferedImage bufferedImage = captcha.getImage();
        ImageIO.write(bufferedImage, "png", out);
        out.close();
    }

}