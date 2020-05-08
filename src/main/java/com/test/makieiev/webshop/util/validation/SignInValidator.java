package com.test.makieiev.webshop.util.validation;

import com.test.makieiev.webshop.model.dto.SignInUserFormDto;
import com.test.makieiev.webshop.util.constant.UserConstant;

import java.util.Map;
import java.util.Objects;

public class SignInValidator {

    public void getSignInErrors(SignInUserFormDto signInUserFormDto, Map<String, Boolean> errors) {
        validateLogin(signInUserFormDto.getLogin(), errors);
        validatePassword(signInUserFormDto.getPassword(), errors);
    }

    private void validateLogin(String login, Map<String, Boolean> errors) {
        if (!isValidLine(login)) {
            errors.put(UserConstant.LOGIN, true);
        }
    }

    private boolean isValidPassword(String line) {
        return isValidLine(line) && line.length() >= 6;
    }

    private boolean isValidLine(String line) {
        return !Objects.isNull(line) && !line.isEmpty();
    }

    private void validatePassword(String password, Map<String, Boolean> errors) {
        if (!isValidPassword(password)) {
            errors.put(UserConstant.PASSWORD, true);
        }
    }

}