package com.test.makieiev.webshop.web.servlet.pages;

import com.test.makieiev.webshop.exception.DBException;
import com.test.makieiev.webshop.exception.InvalidConnectionException;
import com.test.makieiev.webshop.exception.NotFoundItemException;
import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.model.item.Category;
import com.test.makieiev.webshop.model.item.Producer;
import com.test.makieiev.webshop.model.item.Product;
import com.test.makieiev.webshop.service.CategoryService;
import com.test.makieiev.webshop.service.ProducerService;
import com.test.makieiev.webshop.service.ProductService;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.Path;
import com.test.makieiev.webshop.util.constant.RequestAttributeConstant;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.sql.ItemSqlConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(Link.ITEMS)
public class ItemsPageServlet extends HttpServlet {

    private static final long serialVersionUID = 7999858175526177153L;
    private static final int BASIC_ITEMS_AMOUNT = 6;

    private CategoryService categoryService;
    private ProducerService producerService;
    private ProductService productService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        categoryService = (CategoryService) servletContext.getAttribute(ServletContextConstant.CATEGORY_SERVICE);
        producerService = (ProducerService) servletContext.getAttribute(ServletContextConstant.PRODUCER_SERVICE);
        productService = (ProductService) servletContext.getAttribute(ServletContextConstant.PRODUCT_SERVICE);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryService.getAll();
        List<Producer> producers = producerService.getAll();
        checkItemAttribute(request);
        request.setAttribute(RequestAttributeConstant.CATEGORIES, categories);
        request.setAttribute(RequestAttributeConstant.PRODUCERS, producers);
        request.getRequestDispatcher(Path.ITEMS_PAGE).forward(request, response);
    }

    private void checkItemAttribute(HttpServletRequest request) {
        try {
            checkItemAttributeHandler(request);
        } catch (NotFoundItemException ex) {
            request.setAttribute(RequestAttributeConstant.EMPTY_ITEMS, true);
        } catch (DBException ex) {
            request.setAttribute(RequestAttributeConstant.DB_PROBLEM, true);
        } catch (InvalidConnectionException ex) {
            request.setAttribute(RequestAttributeConstant.CONNECTION_PROBLEM, true);
        }
    }

    private void checkItemAttributeHandler(HttpServletRequest request) {
        List<Product> products;
        long itemsAmount;
        int itemsViewAmount;
        if (isEmptyItemAttribute(request)) {
            itemsAmount = productService.getAmount();
            itemsViewAmount = getViewAmount(request);
            products = productService.getAllSortedAndFiltered(ItemSqlConstants.SELECT_LIMIT_ELECTRONIC_PAGE);
        } else {
            String query = (String) request.getAttribute(RequestAttributeConstant.QUERY_ITEMS);
            products = productService.getAllSortedAndFiltered(query);
            String basicQuery = (String) request.getAttribute(RequestAttributeConstant.BASIC_QUERY_ITEMS);
            List<Product> electronicsWithoutPagination = productService.getAllSortedAndFiltered(basicQuery);
            itemsAmount = electronicsWithoutPagination.isEmpty() ? productService.getAmount() : electronicsWithoutPagination.size();
            itemsViewAmount = getViewAmount(request);
            checkItems(products, request);
        }
        request.setAttribute(RequestAttributeConstant.ITEMS, products);
        calculatePages(itemsAmount, itemsViewAmount, request);
    }

    private int getViewAmount(HttpServletRequest request) {
        if (isEmptyViewAmountParameter(request)) {
            return BASIC_ITEMS_AMOUNT;
        } else {
            return checkItemAmount(request);
        }
    }

    private int checkItemAmount(HttpServletRequest request) {
        ProductFilterDto productFilterDto = (ProductFilterDto) request.getAttribute(RequestAttributeConstant.FILTERS);
        if (productFilterDto.getAmountItem() <= 0) {
            return BASIC_ITEMS_AMOUNT;
        }
        return productFilterDto.getAmountItem();
    }

    private boolean isEmptyViewAmountParameter(HttpServletRequest request) {
        return Objects.isNull(request.getAttribute(RequestAttributeConstant.FILTERS));
    }

    private boolean isEmptyItemAttribute(HttpServletRequest request) {
        return Objects.isNull(request.getAttribute(RequestAttributeConstant.QUERY_ITEMS));
    }

    private void checkItems(List<Product> products, HttpServletRequest req) {
        if (products.isEmpty()) {
            req.setAttribute(RequestAttributeConstant.EMPTY_ITEMS, true);
        }
    }

    private void calculatePages(long itemsAmount, long itemsViewAmount, HttpServletRequest request) {
        int pages = (int) Math.ceil((float) itemsAmount / itemsViewAmount);
        request.setAttribute(RequestAttributeConstant.PAGE_NUMBERS, pages);
    }

}