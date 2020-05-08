package com.test.makieiev.webshop.util.validation;

import com.test.makieiev.webshop.model.dto.AddressDto;

import java.util.Map;

public class AddressValidator {

    private static final String REGEXP_STRING_WITHOUT_SYMBOLS = "^[A-Za-z]+$";

    public void validateAddress(AddressDto addressDto, Map<String, Boolean> errors) {
        validateCountry(addressDto.getCountry(), errors);
        validateCity(addressDto.getCity(), errors);
        validateStreet(addressDto.getStreet(), errors);
        validateFloor(addressDto.getFloor(), errors);
        validatePost(addressDto.getPost(), errors);
        validateHouseNumber(addressDto.getHouseNumber(), errors);
    }

    private void validateCountry(String country, Map<String, Boolean> errors) {
        if (!country.matches(REGEXP_STRING_WITHOUT_SYMBOLS)) {
            errors.put("inputCountry", true);
        }
    }

    private void validateCity(String city, Map<String, Boolean> errors) {
        if (!city.matches(REGEXP_STRING_WITHOUT_SYMBOLS)) {
            errors.put("inputCity", true);
        }
    }

    private void validateStreet(String street, Map<String, Boolean> errors) {
        if (!street.matches(REGEXP_STRING_WITHOUT_SYMBOLS)) {
            errors.put("inputStreet", true);
        }
    }

    private void validateFloor(int floor, Map<String, Boolean> errors) {
        if (isNotPositiveNumber(floor)) {
            errors.put("inputFloor", true);
        }
    }

    private void validatePost(int post, Map<String, Boolean> errors) {
        if (isNotPositiveNumber(post)) {
            errors.put("inputPost", true);
        }
    }

    private void validateHouseNumber(int houseNumber, Map<String, Boolean> errors) {
        if (isNotPositiveNumber(houseNumber)) {
            errors.put("inputHouseNumber", true);
        }
    }

    private boolean isNotPositiveNumber(int number) {
        return number < 0;
    }

}