package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Link.ORDER)
public class OrderPageServlet extends HttpServlet {

    private static final long serialVersionUID = 3760337355821466081L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.ORDER_PAGE).forward(req, resp);
    }

}