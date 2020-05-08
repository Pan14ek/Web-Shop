package com.test.makieiev.webshop.web.servlet;

import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.content.ContentInstallation;
import com.test.makieiev.webshop.util.content.impl.JpgContentInstallation;
import com.test.makieiev.webshop.util.content.impl.PngContentInstallation;
import org.junit.Before;
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
import java.util.Map;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GenerateAvatarImageServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession httpSession;

    @Mock
    private ServletOutputStream out;

    @Mock
    private ServletContext servletContext;

    @Mock
    private ServletConfig servletConfig;

    @Before
    public void init() {
        Mockito.when(servletContext.getAttribute(ServletContextConstant.INSTALLATION_CONTENT)).thenReturn(getInstallations());
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
    }

    private Map<String, ContentInstallation> getInstallations() {
        Map<String, ContentInstallation> installations = new HashMap<>();
        installations.put("png", new PngContentInstallation());
        installations.put("jpeg", new JpgContentInstallation());
        return installations;
    }

    @Test
    public void shouldGenerateJpegAvatarImage() throws ServletException, IOException {
        User user = new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "C:\\Users\\Oleksii_Makieiev1\\preproduction_practice\\uploads\\avatar\\809bb624-ae83-4291-bf05-b56b12b9d63d.jpeg", new Role(1, "RegisteredUser"));

        GenerateAvatarImageServlet generateAvatarImageServlet = new GenerateAvatarImageServlet();

        Mockito.when(response.getOutputStream()).thenReturn(out);
        Mockito.when(httpSession.getAttribute(UserConstant.USER)).thenReturn(user);
        Mockito.when(request.getSession()).thenReturn(httpSession);

        generateAvatarImageServlet.init(servletConfig);
        generateAvatarImageServlet.doGet(request, response);

        Mockito.verify(response).setContentType("image/jpeg");
    }

    @Test
    public void shouldGeneratePngAvatarImage() throws IOException, ServletException {
        User user = new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "C:\\Users\\Oleksii_Makieiev1\\preproduction_practice\\uploads\\avatar\\png-2702691_960_720.png", new Role(1, "RegisteredUser"));
        GenerateAvatarImageServlet generateAvatarImageServlet = new GenerateAvatarImageServlet();

        Mockito.when(response.getOutputStream()).thenReturn(out);
        Mockito.when(httpSession.getAttribute(UserConstant.USER)).thenReturn(user);
        Mockito.when(request.getSession()).thenReturn(httpSession);

        generateAvatarImageServlet.init(servletConfig);
        generateAvatarImageServlet.doGet(request, response);

        Mockito.verify(response).setContentType("image/png");
    }

}