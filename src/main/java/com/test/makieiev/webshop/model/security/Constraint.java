package com.test.makieiev.webshop.model.security;

import com.test.makieiev.webshop.model.user.Role;

import java.util.List;

public class Constraint {

    private String urlPattern;
    private List<Role> role;

    public Constraint() {
    }

    public Constraint(String urlPattern, List<Role> role) {
        this.urlPattern = urlPattern;
        this.role = role;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Constraint{" + "urlPattern='" + urlPattern + '\'' +
                ", role=" + role +
                '}';
    }

}