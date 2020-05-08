package com.test.makieiev.webshop.web.servlet;

import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.content.ContentInstallation;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@WebServlet(Link.IMAGE)
public class GenerateImageServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GenerateImageServlet.class);

    private static final char DOT = '.';
    private static final String LINK = "link";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String link = request.getParameter(LINK);
            String type = link.substring(getIndexType(link));
            File imageFile = new File(link);
            setContentType(type, response);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ImageIO.write(bufferedImage, type, response.getOutputStream());
        } catch (IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private int getIndexType(String imageLink) {
        return imageLink.indexOf(DOT) + 1;
    }

    private void setContentType(String type, HttpServletResponse response) {
        Map<String, ContentInstallation> contentInstallationMap = (Map<String, ContentInstallation>) getServletContext()
                .getAttribute(ServletContextConstant.INSTALLATION_CONTENT);
        contentInstallationMap.get(type).install(response);
    }

}