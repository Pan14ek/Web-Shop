package com.test.makieiev.webshop.model.dto;

import java.util.List;

public class ProductFilterDto {

    private List<String> categories;
    private List<String> producers;
    private List<Double> price;
    private String sort;
    private String searchInformation;
    private int page;
    private int amountItem;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public List<Double> getPrice() {
        return price;
    }

    public void setPrice(List<Double> price) {
        this.price = price;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSearchInformation() {
        return searchInformation;
    }

    public void setSearchInformation(String searchInformation) {
        this.searchInformation = searchInformation;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getAmountItem() {
        return amountItem;
    }

    public void setAmountItem(int amountItem) {
        this.amountItem = amountItem;
    }

}