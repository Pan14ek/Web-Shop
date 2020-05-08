package com.test.makieiev.webshop.model.item;

import java.util.Objects;

public class Producer {

    private long id;
    private String title;
    private String city;
    private String country;

    public Producer() {
    }

    public Producer(String title, String city, String country) {
        this.title = title;
        this.city = city;
        this.country = country;
    }

    public Producer(long id, String title, String city, String country) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.country = country;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Producer producer = (Producer) o;
        return getTitle().equals(producer.getTitle()) &&
                getCity().equals(producer.getCity()) &&
                getCountry().equals(producer.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getCity(), getCountry());
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id='" + getId() + '\'' +
                "title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

}