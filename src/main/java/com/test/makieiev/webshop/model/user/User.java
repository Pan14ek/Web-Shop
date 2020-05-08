package com.test.makieiev.webshop.model.user;

import java.util.Objects;

public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private boolean receivingNewsletter;
    private String imageLink;
    private Address address;
    private Role role;

    public User() {
    }

    public User(String firstName, String lastName, String login, String email, String password, boolean receivingNewsletter, String imageLink, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.receivingNewsletter = receivingNewsletter;
        this.imageLink = imageLink;
        this.role = role;
    }

    public User(long id, String firstName, String lastName, String login, String email, String password, boolean receivingNewsletter, String imageLink, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.receivingNewsletter = receivingNewsletter;
        this.imageLink = imageLink;
        this.role = role;
    }

    public User(long id, String firstName, String lastName, String login, String email, String password, boolean receivingNewsletter, String imageLink, Address address, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.receivingNewsletter = receivingNewsletter;
        this.imageLink = imageLink;
        this.address = address;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReceivingNewsletter() {
        return receivingNewsletter;
    }

    public void setReceivingNewsletter(boolean receivingNewsletter) {
        this.receivingNewsletter = receivingNewsletter;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                receivingNewsletter == user.receivingNewsletter &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), firstName, lastName, login, email, receivingNewsletter);
    }

    @Override
    public String toString() {
        return "User[" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", receivingNewsletter=" + receivingNewsletter +
                ']';
    }

}