package com.test.makieiev.webshop.model.order;

import com.test.makieiev.webshop.model.item.Product;

import java.math.BigDecimal;
import java.util.Objects;

public final class OrderItem {

    private long id;
    private final Product product;
    private final int amount;
    private final BigDecimal price;

    public OrderItem(Product product, int amount, BigDecimal price) {
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    public OrderItem(long id, Product product, int amount, BigDecimal price) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return getId() == orderItem.getId() &&
                getAmount() == orderItem.getAmount() &&
                Double.compare(orderItem.getPrice().doubleValue(), getPrice().doubleValue()) == 0 &&
                getProduct().equals(orderItem.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getAmount(), getPrice());
    }

    @Override
    public String toString() {
        return "OrderItem{" + "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

}