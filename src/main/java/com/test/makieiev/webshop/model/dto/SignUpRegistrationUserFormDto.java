package com.test.makieiev.webshop.model.dto;

public class SignUpRegistrationUserFormDto {

    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private String repeatPassword;
    private String userCaptchaAnswer;
    private boolean receivingNewsletter;
    private ImageDto imageDto;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getUserCaptchaAnswer() {
        return userCaptchaAnswer;
    }

    public void setUserCaptchaAnswer(String userCaptchaAnswer) {
        this.userCaptchaAnswer = userCaptchaAnswer;
    }

    public boolean isReceivingNewsletter() {
        return receivingNewsletter;
    }

    public void setReceivingNewsletter(boolean receivingNewsletter) {
        this.receivingNewsletter = receivingNewsletter;
    }

    public ImageDto getImageDto() {
        return imageDto;
    }

    public void setImageDto(ImageDto imageDto) {
        this.imageDto = imageDto;
    }
}