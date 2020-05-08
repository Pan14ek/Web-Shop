package com.test.makieiev.webshop.model.dto;

public class SignInUserFormDto {

    private String login;
    private String password;

    public SignInUserFormDto() {
    }

    public SignInUserFormDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
