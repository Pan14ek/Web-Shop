package com.test.makieiev.webshop.web.servlet;

import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.RequestAttributeConstant;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.sql.ItemSqlConstants;
import com.test.makieiev.webshop.util.filter.Filter;
import com.test.makieiev.webshop.util.validation.ProductFilterValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(Link.OPERATE)
public class ItemOperationServlet extends HttpServlet {

    private DtoCreator dtoCreator;
    private Filter filter;
    private Filter basicFilter;
    private ProductFilterValidator productFilterValidator;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        dtoCreator = (DtoCreator) servletContext.getAttribute(ServletContextConstant.DTO_CREATOR);
        filter = (Filter) servletContext.getAttribute(ServletContextConstant.ITEM_FILTER);
        basicFilter = (Filter) servletContext.getAttribute(ServletContextConstant.ITEM_FILTER_WITHOUT_PAGINATION);
        productFilterValidator = (ProductFilterValidator) servletContext.getAttribute(ServletContextConstant.PRODUCT_FILTER_VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, Boolean> errors = new HashMap<>();
        ProductFilterDto productFilterDto = getFilterDto(request);
        productFilterValidator.getProductFilterErrors(productFilterDto, errors);
        StringBuilder startQuery = new StringBuilder().append(ItemSqlConstants.SELECT_ELECTRONIC_JOIN);
        StringBuilder startQueryWithoutPagination = new StringBuilder().append(ItemSqlConstants.SELECT_ELECTRONIC_JOIN);
        String query = filter.doFilter(startQuery, productFilterDto).toString();
        String queryWithoutPagination = basicFilter.doFilter(startQueryWithoutPagination, productFilterDto).toString();
        request.setAttribute(RequestAttributeConstant.BASIC_QUERY_ITEMS, queryWithoutPagination);
        request.setAttribute(RequestAttributeConstant.QUERY_ITEMS, query);
        request.setAttribute(RequestAttributeConstant.FILTERS, productFilterDto);
        request.setAttribute(RequestAttributeConstant.ERRORS, errors);
        request.getRequestDispatcher(Link.ITEMS).forward(request, response);
    }

    private ProductFilterDto getFilterDto(HttpServletRequest request) {
        if (Objects.isNull(request.getAttribute(RequestAttributeConstant.FILTERS))) {
            return dtoCreator.getFilterDto(request);
        }
        return (ProductFilterDto) request.getAttribute(RequestAttributeConstant.FILTERS);
    }

}