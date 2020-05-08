package com.test.makieiev.webshop.web.servlet.order;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.test.makieiev.webshop.exception.NotFoundItemException;
import com.test.makieiev.webshop.model.item.Product;
import com.test.makieiev.webshop.model.order.Basket;
import com.test.makieiev.webshop.service.ProductService;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.RequestAttributeConstant;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@WebServlet(Link.BASKET)
public class BasketServlet extends HttpServlet {

    private static final long serialVersionUID = -538747736831039836L;
    private static final String ID = "id";
    private static final String AMOUNT = "amount";

    private static final Logger LOGGER = Logger.getLogger(BasketServlet.class);

    private ProductService productService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        productService = (ProductService) servletContext.getAttribute(ServletContextConstant.PRODUCT_SERVICE);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Basket basket = getBasket(request);
        long id = Long.parseLong(request.getParameter(ID));
        basket.remove(id);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            doPostHandler(request);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void doPostHandler(HttpServletRequest request) throws IOException {
        Gson gson = new Gson();
        Basket basket = getBasket(request);
        BufferedReader reader = request.getReader();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        long id = jsonObject.get(ID).getAsLong();
        int amount = jsonObject.get(AMOUNT).getAsInt();
        checkItemInBasket(basket, id, amount, request);
        setBasketInSession(request, basket);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            Gson gson = new Gson();
            Basket basket = getBasket(request);
            BufferedReader reader = request.getReader();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            long id = jsonObject.get(ID).getAsLong();
            int amount = jsonObject.get(AMOUNT).getAsInt();
            basket.update(id, amount);
            setBasketInSession(request, basket);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void checkItemInBasket(Basket basket, long id, int amount, HttpServletRequest request) {
        try {
            checkItemInBasketHandler(basket, id, amount);
        } catch (NotFoundItemException ex) {
            request.setAttribute("notItem", ex.getMessage());
        }
    }

    private void checkItemInBasketHandler(Basket basket, long id, int amount) {
        if (basket.containsId(id)) {
            basket.update(id, amount);
        } else {
            Optional<Product> product = productService.getById(id);
            product.ifPresent(value -> basket.add(value, amount));
        }
    }

    private Basket getBasket(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        return Objects.isNull(httpSession.getAttribute(RequestAttributeConstant.BASKET)) ? new Basket() : (Basket) httpSession.getAttribute(RequestAttributeConstant.BASKET);
    }

    private void setBasketInSession(HttpServletRequest httpServletRequest, Basket basket) {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute(RequestAttributeConstant.BASKET, basket);
    }

}