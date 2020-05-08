package com.test.makieiev.webshop.web.servlet;

import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ItemOperationServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ServletContext servletContext;

    @Mock
    private DtoCreator dtoCreator;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private Filter filterWithPagination;

    @Mock
    private Filter basicFilter;

    @Mock
    private StringBuilder query;

    @Test
    public void shouldOperateWithFilteredInformation() throws IOException, ServletException {
        ItemOperationServlet itemOperationServlet = new ItemOperationServlet();
        ProductFilterDto productFilterDto = new ProductFilterDto();
        productFilterDto.setPage(3);
        Mockito.when(filterWithPagination.doFilter(query, productFilterDto)).thenReturn(query);
        Mockito.when(basicFilter.doFilter(query, productFilterDto)).thenReturn(query);
        Mockito.when(request.getRequestDispatcher(Link.ITEMS)).thenReturn(requestDispatcher);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.ITEM_FILTER)).thenReturn(filterWithPagination);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.ITEM_FILTER_WITHOUT_PAGINATION)).thenReturn(basicFilter);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.DTO_CREATOR)).thenReturn(dtoCreator);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
        itemOperationServlet.init(servletConfig);
        itemOperationServlet.doGet(request, response);
        Mockito.verify(request.getRequestDispatcher(Link.ITEMS), Mockito.times(1)).forward(request, response);
    }

}