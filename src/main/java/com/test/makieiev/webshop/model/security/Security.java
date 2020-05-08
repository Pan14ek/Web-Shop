package com.test.makieiev.webshop.model.security;

import java.util.List;

public class Security {

    private List<Constraint> constraint;

    public Security() {
    }

    public Security(List<Constraint> constraint) {
        this.constraint = constraint;
    }

    public List<Constraint> getConstraint() {
        return constraint;
    }

    public void setConstraint(List<Constraint> constraints) {
        this.constraint = constraints;
    }

    @Override
    public String toString() {
        return "Security{" + "constraint=" + constraint +
                '}';
    }
}