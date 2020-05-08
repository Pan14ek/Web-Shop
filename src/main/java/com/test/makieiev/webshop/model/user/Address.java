package com.test.makieiev.webshop.model.user;

import java.util.Objects;

public class Address {

    private long id;
    private String country;
    private String city;
    private String street;
    private int floor;
    private int post;
    private int houseNumber;

    public Address() {
    }

    public Address(String country, String city, String street, int floor, int post, int houseNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.floor = floor;
        this.post = post;
        this.houseNumber = houseNumber;
    }

    public Address(long id, String country, String city, String street, int floor, int post, int houseNumber) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.floor = floor;
        this.post = post;
        this.houseNumber = houseNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Address address = (Address) obj;
        return Objects.equals(getId(), address.getId()) &&
                Objects.equals(getFloor(), address.getFloor()) &&
                Objects.equals(getPost(), address.getPost()) &&
                Objects.equals(getHouseNumber(), address.getHouseNumber()) &&
                Objects.equals(getCountry(), address.getCountry()) &&
                Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCountry(), getCity(), getStreet(), getFloor(), getPost(), getHouseNumber());
    }
}