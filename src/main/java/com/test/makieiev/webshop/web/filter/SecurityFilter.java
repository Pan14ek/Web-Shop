package com.test.makieiev.webshop.web.filter;

import com.test.makieiev.webshop.model.security.Constraint;
import com.test.makieiev.webshop.model.security.Security;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@WebFilter(Link.ALL)
public class SecurityFilter implements Filter {

    private static final String REFERER = "Referer";
    private static final String LOW_PRIORITY = "lowPriority";

    private static final Logger logger = Logger.getLogger(SecurityFilter.class);

    private Security security;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        security = (Security) servletContext.getAttribute(ServletContextConstant.SECURITY);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String link = httpServletRequest.getRequestURI();
        if (isContainsLink(link)) {
            checkContainsLink(request, response, chain, httpServletRequest, httpServletResponse, link);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void checkContainsLink(ServletRequest request, ServletResponse response,
                                   FilterChain chain, HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse, String link) throws IOException, ServletException {
        if (Objects.nonNull(httpServletRequest.getSession().getAttribute(UserConstant.USER))) {
            checkLinkInConstraintsHandler(request, response, chain, httpServletRequest, httpServletResponse, link);
        } else {
            checkRefererHeader(httpServletRequest, httpServletResponse);
        }
    }

    private void checkRefererHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletRequest.getSession().setAttribute(LOW_PRIORITY, true);
        if (Objects.nonNull(httpServletRequest.getHeader(REFERER))) {
            httpServletResponse.sendRedirect(getRefererLink(httpServletRequest));
        } else {
            httpServletResponse.sendRedirect(Link.INDEX);
        }
    }

    private void checkLinkInConstraintsHandler(ServletRequest request, ServletResponse response,
                                               FilterChain chain, HttpServletRequest httpServletRequest,
                                               HttpServletResponse httpServletResponse, String link) throws IOException, ServletException {
        User user = (User) httpServletRequest.getSession().getAttribute(UserConstant.USER);
        Optional<Constraint> constraint = getConstraint(link);
        if (constraint.isPresent()) {
            checkRoles(request, response, chain, httpServletResponse, user, constraint.get(), httpServletRequest);
        }
    }

    private void checkRoles(ServletRequest request, ServletResponse response,
                            FilterChain chain, HttpServletResponse httpServletResponse,
                            User user, Constraint constraint, HttpServletRequest httpServletRequest) throws IOException, ServletException {
        List<Role> roles = constraint.getRole();
        checkRole(roles, request, response, chain, httpServletResponse, user, httpServletRequest);
    }

    private void checkRole(List<Role> roles, ServletRequest request, ServletResponse response,
                           FilterChain chain, HttpServletResponse httpServletResponse,
                           User user, HttpServletRequest httpServletRequest) throws IOException, ServletException {
        if (validRole(roles, user)) {
            chain.doFilter(request, response);
        } else {
            httpServletRequest.getSession().setAttribute(LOW_PRIORITY, true);
            httpServletResponse.sendRedirect(Link.INDEX);
        }
    }

    private boolean validRole(List<Role> roles, User user) {
        return roles.stream()
                .anyMatch(role -> Objects.equals(role.getTitle(), user.getRole().getTitle()));
    }

    @Override
    public void destroy() {

    }

    private Optional<Constraint> getConstraint(String link) {
        List<Constraint> constraints = security.getConstraint();
        return constraints.stream().filter(constraint -> Objects.equals(constraint.getUrlPattern(), link)).findFirst();
    }

    private boolean isContainsLink(String link) {
        List<Constraint> constraints = security.getConstraint();
        return constraints.stream()
                .anyMatch(constraint -> Objects.equals(constraint.getUrlPattern(), link));
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