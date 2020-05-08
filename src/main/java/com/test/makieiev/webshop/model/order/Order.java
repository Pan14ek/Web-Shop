package com.test.makieiev.webshop.model.order;

import com.test.makieiev.webshop.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {

    private long id;
    private String status;
    private String state;
    private LocalDateTime dateTime;
    private User user;
    private List<OrderItem> itemOrders;

    public Order() {
    }

    public Order(String status, String state, LocalDateTime dateTime, User user, List<OrderItem> itemOrders) {
        this.status = status;
        this.state = state;
        this.dateTime = dateTime;
        this.user = user;
        this.itemOrders = itemOrders;
    }

    public Order(long id, String status, String state, LocalDateTime dateTime, User user, List<OrderItem> itemOrders) {
        this.id = id;
        this.status = status;
        this.state = state;
        this.dateTime = dateTime;
        this.user = user;
        this.itemOrders = itemOrders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItemOrders() {
        return itemOrders;
    }

    public void setItemOrders(List<OrderItem> itemOrders) {
        this.itemOrders = itemOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
                getStatus().equals(order.getStatus()) &&
                getState().equals(order.getState()) &&
                getDateTime().equals(order.getDateTime()) &&
                getUser().equals(order.getUser()) &&
                getItemOrders().equals(order.getItemOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus(), getState(), getDateTime(), getUser(), getItemOrders());
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id +
                ", status='" + status + '\'' +
                ", state='" + state + '\'' +
                ", dateTime=" + dateTime +
                ", user=" + user +
                ", itemOrders=" + itemOrders +
                '}';
    }

}