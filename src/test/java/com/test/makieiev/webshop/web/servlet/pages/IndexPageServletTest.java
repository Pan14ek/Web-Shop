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
public class IndexPageServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Test
    public void shouldForwardToIndexPage() throws ServletException, IOException {
        IndexPageServlet indexPageServlet = new IndexPageServlet();

        Mockito.when(request.getRequestDispatcher(Path.INDEX_PAGE)).thenReturn(requestDispatcher);

        indexPageServlet.doGet(request, response);

        Mockito.verify(request.getRequestDispatcher(Path.INDEX_PAGE), Mockito.times(1)).forward(request, response);
    }

}