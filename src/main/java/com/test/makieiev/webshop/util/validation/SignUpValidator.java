package com.test.makieiev.webshop.util.validation;

import cn.apiclub.captcha.Captcha;
import com.test.makieiev.webshop.model.dto.ImageDto;
import com.test.makieiev.webshop.model.dto.SignUpRegistrationUserFormDto;
import com.test.makieiev.webshop.util.constant.SessionConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;

import java.util.Map;
import java.util.Objects;

public class SignUpValidator {

    private static final String REGEXP_EMAIL = "(.+@(\\w+\\W+\\w.*))";
    private static final String REGEXP_STRING_WITHOUT_SYMBOLS = "^[A-Za-z]+$";
    private static final String REGEXP_FILE_TYPE = "(?U).+(jpg|png)";

    public void getSignUpErrors(SignUpRegistrationUserFormDto signUpRegistrationUserFormDto, Captcha captcha, Map<String, Boolean> errors) {
        validateLogin(signUpRegistrationUserFormDto.getLogin(), errors);
        validateFirstName(signUpRegistrationUserFormDto.getFirstName(), errors);
        validateLastName(signUpRegistrationUserFormDto.getLastName(), errors);
        validateEmail(signUpRegistrationUserFormDto.getEmail(), errors);
        validatePassword(signUpRegistrationUserFormDto.getPassword(), errors);
        validateRepeatPassword(signUpRegistrationUserFormDto.getPassword(), signUpRegistrationUserFormDto.getRepeatPassword(), errors);
        validateCaptchaAnswer(captcha.getAnswer(), signUpRegistrationUserFormDto.getUserCaptchaAnswer(), errors);
        validateFileType(signUpRegistrationUserFormDto.getImageDto(), errors);
    }

    private void validateCaptchaAnswer(String captchaAnswer, String userAnswer, Map<String, Boolean> errors) {
        if (!checkCaptcha(captchaAnswer, userAnswer)) {
            errors.put(SessionConstant.CAPTCHA_ERROR, true);
        }
    }

    private void validateEmail(String email, Map<String, Boolean> errors) {
        if (isNotValidEmail(email)) {
            errors.put(UserConstant.EMAIL, true);
        }
    }

    private void validateLastName(String lastName, Map<String, Boolean> errors) {
        if (isNotValidLineWithoutSymbols(lastName)) {
            errors.put(UserConstant.LAST_NAME, true);
        }
    }

    private void validateFirstName(String firstName, Map<String, Boolean> errors) {
        if (isNotValidLineWithoutSymbols(firstName)) {
            errors.put(UserConstant.FIRST_NAME, true);
        }
    }

    private void validateLogin(String login, Map<String, Boolean> errors) {
        if (!isValidLine(login)) {
            errors.put(UserConstant.LOGIN, true);
        }
    }

    private void validatePassword(String password, Map<String, Boolean> errors) {
        if (!isValidPassword(password)) {
            errors.put(UserConstant.PASSWORD, true);
        }
    }

    private void validateRepeatPassword(String password, String repeatPassword, Map<String, Boolean> errors) {
        if (!isValidRepeatPassword(password, repeatPassword)) {
            errors.put(UserConstant.REPEAT_PASSWORD, true);
        }
    }

    private void validateFileType(ImageDto imageDto, Map<String, Boolean> errors) {
        if (isNotValidFileType(imageDto.getFileName())) {
            errors.put(UserConstant.FILE_NAME, true);
        }
    }

    private boolean isValidRepeatPassword(String password, String repeatPassword) {
        return isValidPassword(password) && isValidPassword(repeatPassword) && Objects.equals(password, repeatPassword);
    }

    private boolean isValidPassword(String line) {
        return isValidLine(line) && line.length() >= 6;
    }

    private boolean isNotValidEmail(String email) {
        return !email.matches(REGEXP_EMAIL);
    }

    private boolean isValidLine(String line) {
        return !Objects.isNull(line) && !line.isEmpty();
    }

    private boolean isNotValidLineWithoutSymbols(String line) {
        return !line.matches(REGEXP_STRING_WITHOUT_SYMBOLS);
    }

    private boolean checkCaptcha(String correctCaptchaAnswer, String userCaptchaAnswer) {
        return Objects.equals(correctCaptchaAnswer, userCaptchaAnswer);
    }

    private boolean isNotValidFileType(String fileType) {
        return !fileType.matches(REGEXP_FILE_TYPE);
    }

}