package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.model.item.Category;
import com.test.makieiev.webshop.model.item.Producer;
import com.test.makieiev.webshop.model.item.Product;
import com.test.makieiev.webshop.service.CategoryService;
import com.test.makieiev.webshop.service.ProducerService;
import com.test.makieiev.webshop.service.ProductService;
import com.test.makieiev.webshop.util.constant.Path;
import com.test.makieiev.webshop.util.constant.RequestAttributeConstant;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ItemsPageServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ServletContext servletContext;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProducerService producerService;

    @Mock
    private ProductService productService;

    @Test
    public void shouldForwardToItemsPage() throws ServletException, IOException {
        ItemsPageServlet itemsPageServlet = new ItemsPageServlet();
        List<Producer> producers = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        Mockito.when(categoryService.getAll()).thenReturn(categories);
        Mockito.when(producerService.getAll()).thenReturn(producers);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CATEGORY_SERVICE)).thenReturn(categoryService);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.PRODUCER_SERVICE)).thenReturn(producerService);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.PRODUCT_SERVICE)).thenReturn(productService);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
        Mockito.when(request.getRequestDispatcher(Path.ITEMS_PAGE)).thenReturn(requestDispatcher);
        itemsPageServlet.init(servletConfig);
        itemsPageServlet.doGet(request, response);

        Mockito.verify(request.getRequestDispatcher(Path.ITEMS_PAGE), Mockito.times(1)).forward(request, response);
    }

    @Test
    public void shouldForwardToItemsPageWithFilteredItems() throws ServletException, IOException {
        ItemsPageServlet itemsPageServlet = new ItemsPageServlet();
        List<Producer> producers = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        Mockito.when(request.getAttribute(RequestAttributeConstant.QUERY_ITEMS)).thenReturn("test");
        Mockito.when(categoryService.getAll()).thenReturn(categories);
        Mockito.when(producerService.getAll()).thenReturn(producers);
        Mockito.when(productService.getAllSortedAndFiltered(Mockito.anyString())).thenReturn(products);
        Mockito.when(request.getAttribute(RequestAttributeConstant.BASIC_QUERY_ITEMS)).thenReturn("test1");
        Mockito.when(productService.getAllSortedAndFiltered("test1")).thenReturn(products);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.CATEGORY_SERVICE)).thenReturn(categoryService);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.PRODUCER_SERVICE)).thenReturn(producerService);
        Mockito.when(servletContext.getAttribute(ServletContextConstant.PRODUCT_SERVICE)).thenReturn(productService);
        Mockito.when(servletConfig.getServletContext()).thenReturn(servletContext);
        Mockito.when(request.getRequestDispatcher(Path.ITEMS_PAGE)).thenReturn(requestDispatcher);
        itemsPageServlet.init(servletConfig);
        itemsPageServlet.doGet(request, response);

        Mockito.verify(request.getRequestDispatcher(Path.ITEMS_PAGE), Mockito.times(1)).forward(request, response);
    }

}