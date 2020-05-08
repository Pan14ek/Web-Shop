package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.exception.InvalidFieldException;
import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.dto.SignInUserFormDto;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.UserService;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.SessionConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.validation.SignInValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(Link.AUTHORIZATION)
public class AuthorizationServlet extends HttpServlet {

    private static final long serialVersionUID = 904434361798929654L;
    private static final String REFERER = "referer";

    private static final Logger logger = Logger.getLogger(AuthorizationServlet.class);

    private SignInValidator signInValidator;
    private DtoCreator dtoCreator;
    private UserService userService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        signInValidator = (SignInValidator) servletContext.getAttribute(ServletContextConstant.SIGN_IN_USER_VALIDATOR);
        dtoCreator = (DtoCreator) servletContext.getAttribute(ServletContextConstant.DTO_CREATOR);
        userService = (UserService) servletContext.getAttribute(ServletContextConstant.USER_SERVICE);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Boolean> errors = new HashMap<>();
        SignInUserFormDto signInUserFormDto = dtoCreator.getSignInDto(request);
        String refererLink = getRefererLink(request);
        checkUser(signInUserFormDto, errors, refererLink, request, response);
        failedSignIn(request, response, errors, signInUserFormDto, refererLink);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(Link.INDEX);
    }

    private void checkUser(SignInUserFormDto signInUserFormDto, Map<String, Boolean> errors,
                           String referer, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            signInValidator.getSignInErrors(signInUserFormDto, errors);
            checkUserHandle(request, response, errors, signInUserFormDto, referer);
        } catch (InvalidFieldException ex) {
            errors.put(UserConstant.INCORRECT_PASSWORD, true);
        }
    }

    private void checkUserHandle(HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Boolean> errors, SignInUserFormDto signInUserFormDto, String referer) throws IOException {
        if (errors.isEmpty()) {
            Optional<User> optionalUser = userService.getByLogin(signInUserFormDto.getLogin());
            if (optionalUser.isPresent()) {
                optionalUser.ifPresent(user -> userService.checkPassword(user, signInUserFormDto.getPassword()));
                successSignIn(request, response, optionalUser.get(), referer);
            } else {
                errors.put(UserConstant.INCORRECT_LOGIN, true);
            }
        }
    }

    private void successSignIn(HttpServletRequest request, HttpServletResponse response,
                               User user, String referer) throws IOException {
        request.getSession().setAttribute(UserConstant.USER, user);
        response.sendRedirect(referer);
    }

    private void failedSignIn(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Boolean> errors, SignInUserFormDto signInUserFormDto, String referer) throws IOException {
        if (!errors.isEmpty()) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(UserConstant.AUTHORIZATION_USER_TEMPORARY, signInUserFormDto);
            httpSession.setAttribute(SessionConstant.SIGN_IN_ERRORS, errors);
            response.sendRedirect(referer);
        }
    }

    private String getRefererLink(HttpServletRequest request) {
        String referer = null;
        try {
            referer = new URI(request.getHeader(REFERER)).getPath();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
        return referer;
    }

}