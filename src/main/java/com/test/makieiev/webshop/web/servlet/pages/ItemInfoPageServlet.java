package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Link.ITEM_INFO)
public class ItemInfoPageServlet extends HttpServlet {

    private static final long serialVersionUID = 967024973376780115L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Path.ITEM_INFO_PAGE).forward(request, response);
    }

}