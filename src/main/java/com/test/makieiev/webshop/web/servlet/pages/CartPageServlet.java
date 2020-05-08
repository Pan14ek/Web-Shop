package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Link.CART)
public class CartPageServlet extends HttpServlet {

    private static final long serialVersionUID = 7002727673996074556L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Path.CART_PAGE).forward(request, response);
    }

}