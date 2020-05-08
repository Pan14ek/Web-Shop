package com.test.makieiev.webshop.web.servlet.order;

import com.google.gson.Gson;
import com.test.makieiev.webshop.model.dto.AddressDto;
import com.test.makieiev.webshop.model.dto.DtoConverter;
import com.test.makieiev.webshop.model.dto.DtoCreator;
import com.test.makieiev.webshop.model.order.Basket;
import com.test.makieiev.webshop.model.order.Order;
import com.test.makieiev.webshop.model.order.OrderItem;
import com.test.makieiev.webshop.model.user.Address;
import com.test.makieiev.webshop.model.user.User;
import com.test.makieiev.webshop.service.OrderService;
import com.test.makieiev.webshop.service.UserService;
import com.test.makieiev.webshop.util.constant.Link;
import com.test.makieiev.webshop.util.constant.RequestAttributeConstant;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import com.test.makieiev.webshop.util.validation.AddressValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(Link.ORDER_OPERATION)
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1825569894108230695L;
    private static final String START = "Start";

    private static final Logger LOGGER = Logger.getLogger(OrderServlet.class);

    private OrderService orderService;
    private DtoCreator dtoCreator;
    private AddressValidator addressValidator;
    private DtoConverter dtoConverter;
    private UserService userService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        orderService = (OrderService) servletContext.getAttribute(ServletContextConstant.ORDER_SERVICE);
        dtoCreator = (DtoCreator) servletContext.getAttribute(ServletContextConstant.DTO_CREATOR);
        addressValidator = (AddressValidator) servletContext.getAttribute(ServletContextConstant.ADDRESS_VALIDATOR);
        dtoConverter = (DtoConverter) servletContext.getAttribute(ServletContextConstant.DTO_CONVERTER);
        userService = (UserService) servletContext.getAttribute(ServletContextConstant.USER_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            doPostHandler(request, response);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void doPostHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<Basket> basket = getBasket(request);
        if (basket.isPresent()) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Map<String, Boolean> errors = new HashMap<>();
            AddressDto addressDto = dtoCreator.getAddressDto(request);
            addressValidator.validateAddress(addressDto, errors);
            checkErrors(request, basket.get(), out, errors, addressDto);
        }
    }

    private void checkErrors(HttpServletRequest request, Basket basket, PrintWriter out, Map<String, Boolean> errors, AddressDto addressDto) {
        if (errors.isEmpty()) {
            String update = new Gson().toJson(addOrder(request, addressDto, basket));
            request.getSession().removeAttribute(RequestAttributeConstant.BASKET);
            out.print(update);
            out.flush();
        } else {
            String errorsMapJson = new Gson().toJson(errors);
            out.print(errorsMapJson);
            out.flush();
        }
    }

    private boolean addOrder(HttpServletRequest request, AddressDto addressDto, Basket basket) {
        Address address = dtoConverter.getAddress(addressDto);
        long id = userService.addAddress(address);
        address.setId(id);
        User user = getUser(request);
        user.setAddress(address);
        userService.updateUser(user);
        Order order = new Order();
        setOrderParameters(order, basket, user);
        return orderService.add(order);
    }

    private void setOrderParameters(Order order, Basket basket, User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<OrderItem> orderItems = new ArrayList<>();
        basket.getItems().forEach((product, amount) -> orderItems.add(new OrderItem(product, amount, product.getPrice())));
        order.setItemOrders(orderItems);
        order.setDateTime(localDateTime);
        order.setUser(user);
        order.setStatus(START);
        order.setState(START);
    }

    private Optional<Basket> getBasket(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        return Optional.ofNullable((Basket) httpSession.getAttribute(RequestAttributeConstant.BASKET));
    }

    private User getUser(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        return (User) httpSession.getAttribute(UserConstant.USER);
    }

}