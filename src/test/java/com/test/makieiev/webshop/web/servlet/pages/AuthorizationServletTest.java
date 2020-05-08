package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.exception.InvalidFieldException;
import com.test.makieiev.webshop.model.dto.DtoConverter;
import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.dto.SignInUserFormDto;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.UserService;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.validation.SignInValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AuthorizationServletTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private HttpSession httpSession;

    @Mock
    private UserDao userDao;

    @Mock
    private Connection connection;

    @Mock
    private ServletContext servletContext;

    @Mock
    private UserService userService;

    @Test
    public void shouldLogIn() throws ServletException, IOException {
        DtoCreator dtoCreator = new DtoCreator();
        DtoConverter dtoConverter = new DtoConverter();
        User user = Mockito.mock(User.class);

        Mockito.when(httpSession.getAttribute(UserConstant.USER)).thenReturn(user);
        Mockito.when(userDao.getUserByLogin("six", connection)).thenReturn(Optional.of(user));
        Mockito.when(userService.getByLogin("six")).thenReturn(Optional.of(user));

        SignInValidator signInValidator = new SignInValidator();

        Mockito.when(httpServletRequest.getSession()).thenReturn(httpSession);
        Mockito.when(httpServletRequest.getHeader("referer")).thenReturn("/");
        Mockito.when(servletContext.getAttribute(ServletContextConstant.USER_SERVICE)).thenReturn(userService);
        Mockito.when(httpServletRequest.getParameter(UserConstant.INPUT_LOGIN)).thenReturn("six");
        Mockito.when(httpServletRequest.getParameter(UserConstant.INPUT_PASSWORD)).thenReturn("1234567");
        Mockito.when(servletContext.getAttribute(ServletContextConstant.SIGN_IN_USER_VALIDATOR)).thenReturn(signInValidator);
        Mockito.when(httpServletRequest.getServletContext()).thenReturn(servletContext);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.DTO_CONVERTER)).thenReturn(dtoConverter);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.DTO_CREATOR)).thenReturn(dtoCreator);

        ServletConfig servletConfig = Mockito.mock(ServletConfig.class);

        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);

        AuthorizationServlet authorizationServlet = new AuthorizationServlet();

        authorizationServlet.init(servletConfig);
        authorizationServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/");
    }

    @Test
    public void shouldGetNegativeResult() throws ServletException, IOException {
        User user = new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "img", new Role(1, "RegisteredUser"));
        DtoCreator dtoCreator = new DtoCreator();
        DtoConverter dtoConverter = new DtoConverter();

        Mockito.when(httpSession.getAttribute(UserConstant.USER)).thenReturn(user);
        Mockito.when(userDao.getUserByLogin("six", connection)).thenReturn(Optional.of(user));
        Mockito.when(userService.getByLogin("six")).thenReturn(Optional.empty());

        SignInValidator signInValidator = new SignInValidator();
        Mockito.when(httpServletRequest.getSession()).thenReturn(httpSession);
        Mockito.when(httpServletRequest.getHeader("referer")).thenReturn("/");
        Mockito.doThrow(new InvalidFieldException("")).when(userService).checkPassword(user, new SignInUserFormDto().getPassword());
        Mockito.when(servletContext.getAttribute(ServletContextConstant.USER_SERVICE)).thenReturn(userService);
        Mockito.when(httpServletRequest.getParameter(UserConstant.INPUT_LOGIN)).thenReturn("six");
        Mockito.when(httpServletRequest.getParameter(UserConstant.INPUT_PASSWORD)).thenReturn("1234567");
        Mockito.when(servletContext.getAttribute(ServletContextConstant.SIGN_IN_USER_VALIDATOR)).thenReturn(signInValidator);
        Mockito.when(httpServletRequest.getServletContext()).thenReturn(servletContext);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.DTO_CONVERTER)).thenReturn(dtoConverter);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.DTO_CREATOR)).thenReturn(dtoCreator);

        ServletConfig servletConfig = Mockito.mock(ServletConfig.class);

        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);

        AuthorizationServlet authorizationServlet = new AuthorizationServlet();

        authorizationServlet.init(servletConfig);
        authorizationServlet.doPost(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect("/");
    }

    @Test
    public void shouldLogOut() throws IOException {
        AuthorizationServlet authorizationServlet = new AuthorizationServlet();

        Mockito.when(httpServletRequest.getSession()).thenReturn(httpSession);
        authorizationServlet.doGet(httpServletRequest, httpServletResponse);

        Mockito.verify(httpServletResponse, Mockito.times(1)).sendRedirect(Link.INDEX);
    }

}