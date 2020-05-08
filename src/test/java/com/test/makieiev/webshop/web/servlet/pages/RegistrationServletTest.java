package com.test.makieiev.webshop.web.servlet.pages;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import com.test.makieiev.webshop.dao.UserDao;
import com.test.makieiev.webshop.dao.impl.MemoryUserDaoImpl;
import com.test.makieiev.webshop.dao.impl.TransactionManager;
import com.test.makieiev.webshop.model.dto.DtoConverter;
import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.CaptchaService;
import com.test.makieiev.webshop.service.UploadService;
import com.test.makieiev.webshop.service.UserService;
import com.test.makieiev.webshop.service.impl.CaptchaServiceImpl;
import com.test.makieiev.webshop.service.impl.UserServiceImpl;
import com.test.makieiev.webshop.storage.Storage;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.Path;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.generation.CaptchaStorage;
import com.test.makieiev.webshop.util.generation.CleanerWorker;
import com.test.makieiev.webshop.util.generation.impl.AttributeContextCaptchaStorage;
import com.test.makieiev.webshop.util.generation.impl.CookieContextCaptchaStorage;
import com.test.makieiev.webshop.util.generation.impl.SessionCaptchaStorage;
import com.test.makieiev.webshop.util.validation.SignUpValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RegistrationServletTest {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    private Map<Long, Captcha> map;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession httpSession;

    @Mock
    private ServletContext servletContext;

    @Mock
    private TransactionManager transactionManager;

    @Mock
    private CleanerWorker cleanerWorker;

    @Mock
    private Part imagePart;

    @Mock
    private UploadService uploadService;

    @Before
    public void init() throws IOException, ServletException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Thread worker = new Thread(cleanerWorker);
        worker.setDaemon(true);
        Storage storage = new Storage(initUsers());
        UserDao userDao = new MemoryUserDaoImpl(storage);
        UserService userService = new UserServiceImpl(userDao, transactionManager);
        map = new HashMap<>();
        DtoCreator dtoCreator = new DtoCreator();
        DtoConverter dtoConverter = new DtoConverter();
        Captcha captcha = new Captcha.Builder(WIDTH, HEIGHT)
                .addText()
                .addBorder()
                .gimp()
                .addBackground(new GradiatedBackgroundProducer())
                .build();
        SignUpValidator signUpValidator = new SignUpValidator();

        map.put(1L, captcha);
        Mockito.when(imagePart.getContentType()).thenReturn("png");
        Mockito.when(imagePart.getHeader("content-disposition")).thenReturn("test=test;filename=foto.png");
        Mockito.when(request.getPart(UserConstant.AVATAR)).thenReturn(imagePart);
        Mockito.when(httpSession.getAttribute(Captcha.NAME)).thenReturn(captcha);
        Mockito.when(request.getSession()).thenReturn(httpSession);
        Mockito.when(request.getServletContext()).thenReturn(servletContext);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CLEANER)).thenReturn(worker);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.UPLOAD_SERVICE)).thenReturn(uploadService);
        Mockito.when(servletContext.getInitParameter(ServletContextConstant.UPLOAD_AVATAR_DIRECTORY)).thenReturn("/");

        Mockito.when(servletContext.getAttribute(ServletContextConstant.SIGN_UP_USER_VALIDATOR)).thenReturn(signUpValidator);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.USER_SERVICE)).thenReturn(userService);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.DTO_CONVERTER)).thenReturn(dtoConverter);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.DTO_CREATOR)).thenReturn(dtoCreator);
        Mockito.when(request.getSession().getAttribute("time")).thenReturn(localDateTime);
        Mockito.when(request.getParameter(UserConstant.FIRST_NAME)).thenReturn("name");
        Mockito.when(request.getParameter(UserConstant.LAST_NAME)).thenReturn("lastName");
        Mockito.when(request.getParameter(UserConstant.LOGIN)).thenReturn("login");
        Mockito.when(request.getParameter(UserConstant.EMAIL)).thenReturn("test@test.com");
        Mockito.when(request.getParameter(UserConstant.PASSWORD)).thenReturn("password");
        Mockito.when(request.getParameter(UserConstant.REPEAT_PASSWORD)).thenReturn("password");
        Mockito.when(request.getParameter(UserConstant.RECEIVING_NEWSLETTER)).thenReturn("true");
        Mockito.when(request.getParameter(UserConstant.CAPTCHA)).thenReturn(captcha.getAnswer());
        Mockito.when(request.getServletContext().getAttribute(ServletContextConstant.CAPTCHA_VALUES)).thenReturn(map);
        Mockito.when(request.getServletContext().getInitParameter(ServletContextConstant.TIMEOUT_REGISTRATION)).thenReturn("2");
    }

    @Test
    public void shouldRegisterUserBySessionMethod() throws IOException, ServletException {
        SignUpPageServlet signUpPageServlet = new SignUpPageServlet();
        CaptchaStorage captchaStorage = new SessionCaptchaStorage(map);
        ServletConfig servletConfig = Mockito.mock(ServletConfig.class);

        Mockito.when(uploadService.uploadFile(Mockito.any(Part.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(true);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn(captchaStorage);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
        Mockito.when(servletContext.getInitParameter(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn("session");
        Mockito.when(servletContext.getAttribute(Captcha.NAME)).thenReturn(1L);
        signUpPageServlet.init(servletConfig);
        signUpPageServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).sendRedirect(Link.INDEX);
    }

    @Test
    public void shouldRegisterUserByAttributeMethod() throws IOException, ServletException {
        SignUpPageServlet signUpPageServlet = new SignUpPageServlet();
        CaptchaStorage captchaStorage = new AttributeContextCaptchaStorage(map);
        ServletConfig servletConfig = Mockito.mock(ServletConfig.class);

        Mockito.when(uploadService.uploadFile(Mockito.any(Part.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(true);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn(captchaStorage);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
        Mockito.when(request.getServletContext().getInitParameter(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn("attribute");
        Mockito.when(request.getParameter(Captcha.NAME)).thenReturn(1L + StringUtils.EMPTY);
        signUpPageServlet.init(servletConfig);
        signUpPageServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).sendRedirect(Link.INDEX);
    }

    @Test
    public void shouldRegisterUserByCookieMethod() throws IOException, ServletException {
        SignUpPageServlet signUpPageServlet = new SignUpPageServlet();
        Cookie cookie = new Cookie(Captcha.NAME, 1L + StringUtils.EMPTY);
        cookie.setHttpOnly(true);
        CaptchaStorage captchaStorage = new CookieContextCaptchaStorage(map);
        ServletConfig servletConfig = Mockito.mock(ServletConfig.class);

        Mockito.when(uploadService.uploadFile(Mockito.any(Part.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(true);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn(captchaStorage);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        Mockito.when(request.getServletContext().getInitParameter(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn("cookie");
        signUpPageServlet.init(servletConfig);
        signUpPageServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).sendRedirect(Link.INDEX);
    }

    @Test
    public void shouldGetNegativeResult() throws ServletException, IOException {
        SignUpPageServlet signUpPageServlet = new SignUpPageServlet();
        CaptchaStorage captchaStorage = new AttributeContextCaptchaStorage(map);
        ServletConfig servletConfig = Mockito.mock(ServletConfig.class);

        Mockito.when(uploadService.uploadFile(Mockito.any(Part.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(false);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn(captchaStorage);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
        Mockito.when(request.getServletContext().getInitParameter(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn("attribute");
        Mockito.when(request.getParameter(Captcha.NAME)).thenReturn(1L + StringUtils.EMPTY);
        signUpPageServlet.init(servletConfig);
        signUpPageServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).sendRedirect(Link.SIGN_UP);
    }

    @Test
    public void shouldForwardToSignUpPage() throws ServletException, IOException {
        SignUpPageServlet signUpPageServlet = new SignUpPageServlet();
        CaptchaService captchaService = new CaptchaServiceImpl();
        CaptchaStorage captchaStorage = new CookieContextCaptchaStorage(map);
        ServletConfig servletConfig = Mockito.mock(ServletConfig.class);
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(request.getRequestDispatcher(Path.SIGN_UP_PAGE)).thenReturn(requestDispatcher);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_SERVICE)).thenReturn(captchaService);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE)).thenReturn(captchaStorage);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);

        signUpPageServlet.init(servletConfig);
        signUpPageServlet.doGet(request, response);

        Mockito.verify(request.getRequestDispatcher(Path.SIGN_UP_PAGE), Mockito.times(1)).forward(request, response);
    }

    private Map<Long, User> initUsers() {
        Map<Long, User> users = new HashMap<>();
        Role role = new Role(1, "RegisteredUser");
        users.put(1L, new User(1L, "one", "one", "one", "one@gmail.com", "1234567", true, "img", role));
        users.put(2L, new User(2L, "two", "two", "two", "two@gmail.com", "1234567", false, "img", role));
        users.put(3L, new User(3L, "three", "three", "three", "three@gmail.com", "1234567", true, "img", role));
        users.put(4L, new User(4L, "four", "four", "four", "four@gmail.com", "1234567", true, "img", role));
        users.put(5L, new User(5L, "five", "five", "five", "five@gmail.com", "1234567", true, "img", role));
        users.put(6L, new User(6L, "six", "six", "six", "six@gmail.com", "1234567", true, "img", role));
        return users;
    }

}