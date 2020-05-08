package com.test.makieiev.webshop.model.user;

import java.util.Objects;

public class Role {

    private long id;
    private String title;

    public Role() {
    }

    public Role(String title) {
        this.title = title;
    }

    public Role(long id, String title) {
        this.id = id;
        this.title = title;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, ((Role) o).id) && getTitle().equals(role.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}