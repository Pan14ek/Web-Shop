package com.test.makieiev.webshop.model.item;

import java.util.Objects;

public class Category {

    private long id;
    private String title;
    private String description;

    public Category() {
    }

    public Category(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Category(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId()) &&
                getTitle().equals(category.getTitle()) &&
                getDescription().equals(category.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription());
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + getId() + '\'' +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}