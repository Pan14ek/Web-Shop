package com.test.makieiev.webshop.model.item;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private long id;
    private String title;
    private String description;
    private BigDecimal price;
    private double screenDiagonal;
    private String imageLink;
    private Category category;
    private Producer producer;

    public Product() {
    }

    public Product(String title, String description, BigDecimal price, double screenDiagonal, String imageLink, Category category, Producer producer) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.screenDiagonal = screenDiagonal;
        this.imageLink = imageLink;
        this.category = category;
        this.producer = producer;
    }

    public Product(long id, String title, String description, BigDecimal price, double screenDiagonal, String imageLink, Category category, Producer producer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.screenDiagonal = screenDiagonal;
        this.imageLink = imageLink;
        this.category = category;
        this.producer = producer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getScreenDiagonal() {
        return screenDiagonal;
    }

    public void setScreenDiagonal(double screenDiagonal) {
        this.screenDiagonal = screenDiagonal;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(that.getPrice(), getPrice()) &&
                Double.compare(that.getScreenDiagonal(), getScreenDiagonal()) == 0 &&
                getTitle().equals(that.getTitle()) &&
                getDescription().equals(that.getDescription()) &&
                getImageLink().equals(that.getImageLink()) &&
                getCategory().equals(that.getCategory()) &&
                getProducer().equals(that.getProducer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(),
                getDescription(),
                getPrice(),
                getScreenDiagonal(),
                getImageLink(),
                getCategory(),
                getProducer());
    }

}