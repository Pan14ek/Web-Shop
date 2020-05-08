package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.util.constant.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ItemInfoPageServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Test
    public void shouldForwardToCartPage() throws ServletException, IOException {
        ItemInfoPageServlet itemInfoPageServlet = new ItemInfoPageServlet();

        Mockito.when(request.getRequestDispatcher(Path.ITEM_INFO_PAGE)).thenReturn(requestDispatcher);

        itemInfoPageServlet.doGet(request, response);

        Mockito.verify(request.getRequestDispatcher(Path.ITEM_INFO_PAGE), Mockito.times(1)).forward(request, response);
    }

}