package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Link.ADMIN)
public class AdminPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1780195131092162339L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.ADMIN_PAGE).forward(req, resp);
    }

}
