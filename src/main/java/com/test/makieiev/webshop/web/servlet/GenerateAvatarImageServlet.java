package com.test.makieiev.webshop.web.servlet;

import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
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

@WebServlet(Link.AVATAR)
public class GenerateAvatarImageServlet extends HttpServlet {

    private static final long serialVersionUID = -7741436584988000707L;

    private static final Logger LOGGER = Logger.getLogger(GenerateAvatarImageServlet.class);
    private static final char DOT = '.';

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute(UserConstant.USER);
            String type = user.getImageLink().substring(getIndexType(user.getImageLink()));
            File imageFile = new File(user.getImageLink());
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