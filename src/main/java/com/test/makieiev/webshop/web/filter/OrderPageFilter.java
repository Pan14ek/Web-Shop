package com.test.makieiev.webshop.web.filter;

import com.test.makieiev.webshop.model.order.Basket;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.RequestAttributeConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebFilter(Link.ORDER)
public class OrderPageFilter implements Filter {

    private static final String NOT_AUTHORIZED = "notAuthorized";
    private static final String NO_ITEMS = "noItems";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession();
        if (isNotAuthorizedUser(httpSession)) {
            httpSession.setAttribute(NOT_AUTHORIZED, true);
            httpServletResponse.sendRedirect(Link.CART);
        } else {
            checkBasket(request, response, chain, httpServletResponse, httpSession);
        }
    }

    private void checkBasket(ServletRequest request, ServletResponse response, FilterChain chain,
                             HttpServletResponse httpServletResponse, HttpSession httpSession) throws IOException, ServletException {
        if (Objects.nonNull(httpSession.getAttribute(RequestAttributeConstant.BASKET))) {
            Basket basket = (Basket) httpSession.getAttribute(RequestAttributeConstant.BASKET);
            checkElementsInBasket(request, response, chain, httpServletResponse, httpSession, basket);
        } else {
            httpServletResponse.sendRedirect(Link.CART);
        }
    }

    private void checkElementsInBasket(ServletRequest request, ServletResponse response,
                                       FilterChain chain, HttpServletResponse httpServletResponse,
                                       HttpSession httpSession, Basket basket) throws IOException, ServletException {
        if (basket.getItems().isEmpty()) {
            httpSession.setAttribute(NO_ITEMS, true);
            httpServletResponse.sendRedirect(Link.CART);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isNotAuthorizedUser(HttpSession httpSession) {
        return Objects.isNull(httpSession.getAttribute(UserConstant.USER));
    }

    @Override
    public void destroy() {
    }

}