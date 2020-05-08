package com.test.makieiev.webshop.web.servlet.pages;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.exception.NotUniqueUserException;
import com.test.makieiev.webshop.model.dto.DtoConverter;
import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.dto.SignUpRegistrationUserFormDto;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.CaptchaService;
import com.test.makieiev.webshop.service.UploadService;
import com.test.makieiev.webshop.service.UserService;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.Path;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.SessionConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.generation.CaptchaStorage;
import com.test.makieiev.webshop.util.validation.SignUpValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(Link.SIGN_UP)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class SignUpPageServlet extends HttpServlet {

    private static final long serialVersionUID = -2556011763684354426L;

    private CaptchaStorage captchaStorage;
    private CaptchaService captchaService;
    private UserService userService;
    private UploadService uploadService;
    private DtoConverter dtoConverter;
    private SignUpValidator signUpValidator;
    private DtoCreator dtoCreator;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        captchaStorage = (CaptchaStorage) servletContext.getAttribute(ServletContextConstant.CAPTCHA_STORAGE);
        captchaService = (CaptchaService) servletContext.getAttribute(ServletContextConstant.CAPTCHA_SERVICE);
        userService = (UserService) servletContext.getAttribute(ServletContextConstant.USER_SERVICE);
        uploadService = (UploadService) servletContext.getAttribute(ServletContextConstant.UPLOAD_SERVICE);
        dtoConverter = (DtoConverter) servletContext.getAttribute(ServletContextConstant.DTO_CONVERTER);
        signUpValidator = (SignUpValidator) servletContext.getAttribute(ServletContextConstant.SIGN_UP_USER_VALIDATOR);
        dtoCreator = (DtoCreator) servletContext.getAttribute(ServletContextConstant.DTO_CREATOR);
        Thread worker = (Thread) servletContext.getAttribute(ServletContextConstant.CLEANER);
        worker.start();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Captcha captcha = captchaService.create();
        captchaStorage.put(captcha, request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Path.SIGN_UP_PAGE);
        requestDispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        SignUpRegistrationUserFormDto signUpRegistrationUserFormDto = dtoCreator.getSignUpDto(request);
        Optional<Captcha> captcha = captchaStorage.get(request);
        Map<String, Boolean> errors = new HashMap<>();
        if (captcha.isPresent()) {
            signUpValidator.getSignUpErrors(signUpRegistrationUserFormDto, captcha.get(), errors);
            userRegister(request, response, errors, signUpRegistrationUserFormDto);
        } else {
            errors.put(SessionConstant.TIME, true);
        }
        failedRegistration(request, response, errors, signUpRegistrationUserFormDto);
    }

    private void userRegister(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Boolean> errors, SignUpRegistrationUserFormDto signUpRegistrationUserFormDto) throws IOException, ServletException {
        if (errors.isEmpty()) {
            try {
                registerUserHandle(signUpRegistrationUserFormDto);
                String path = getServletContext().getInitParameter(ServletContextConstant.UPLOAD_AVATAR_DIRECTORY);
                String imageName = signUpRegistrationUserFormDto.getImageDto().getHashFileName();
                Part filePart = request.getPart(UserConstant.AVATAR);
                boolean isUpload = uploadService.uploadFile(filePart, imageName, path);
                checkUpload(isUpload, request, response, errors);
            } catch (NotUniqueUserException ex) {
                errors.put(SessionConstant.EMPLOYED_LOGIN, true);
            }
        }
    }

    private void checkUpload(boolean isUpload, HttpServletRequest request, HttpServletResponse response, Map<String, Boolean> errors) throws IOException {
        if (isUpload) {
            successRegistration(request, response);
        } else {
            errors.put(SessionConstant.FILE, true);
        }
    }

    private void registerUserHandle(SignUpRegistrationUserFormDto signUpRegistrationUserFormDto) {
        User user = dtoConverter.getUser(signUpRegistrationUserFormDto);
        userService.addUser(user);
    }

    private void successRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SessionConstant.SUCCESSFUL, SessionConstant.OK);
        httpSession.removeAttribute(UserConstant.USER_TEMPORARY);
        httpSession.removeAttribute(SessionConstant.ERRORS);
        response.sendRedirect(Link.INDEX);
    }

    private void failedRegistration(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Boolean> errors,
                                    SignUpRegistrationUserFormDto signUpRegistrationUserFormDto) throws IOException {
        if (!errors.isEmpty()) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(UserConstant.USER_TEMPORARY, signUpRegistrationUserFormDto);
            httpSession.setAttribute(SessionConstant.ERRORS, errors);
            response.sendRedirect(Link.SIGN_UP);
        }
    }

}