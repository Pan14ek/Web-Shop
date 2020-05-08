package com.test.makieiev.webshop.model.dto;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.test.makieiev.webshop.util.constant.RequestAttributeConstant;
import com.test.makieiev.webshop.util.constant.ServletContextConstant;
import com.test.makieiev.webshop.util.constant.UserConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DtoCreator {

    private static final String DOT = ".";
    private static final double ZERO = 0;
    private static final int ONE = 1;
    private static final int MIN_ITEM_AMOUNT = 6;
    private static final int MAX_ITEM_AMOUNT = 24;
    private static final String REGEXP_DOUBLE_NUMBER = "(\\d+\\.\\d+|\\d+)";
    private static final String REGEXP_INTEGER_NUMBER = "\\d+";

    public SignUpRegistrationUserFormDto getSignUpDto(HttpServletRequest request) throws IOException, ServletException {
        SignUpRegistrationUserFormDto signUpRegistrationUserFormDto = new SignUpRegistrationUserFormDto();
        signUpRegistrationUserFormDto.setFirstName(request.getParameter(UserConstant.FIRST_NAME));
        signUpRegistrationUserFormDto.setLastName(request.getParameter(UserConstant.LAST_NAME));
        signUpRegistrationUserFormDto.setLogin(request.getParameter(UserConstant.LOGIN));
        signUpRegistrationUserFormDto.setEmail(request.getParameter(UserConstant.EMAIL));
        signUpRegistrationUserFormDto.setPassword(request.getParameter(UserConstant.PASSWORD));
        signUpRegistrationUserFormDto.setRepeatPassword(request.getParameter(UserConstant.REPEAT_PASSWORD));
        signUpRegistrationUserFormDto.setReceivingNewsletter(getReceivingNewsletter(request));
        signUpRegistrationUserFormDto.setUserCaptchaAnswer(request.getParameter(UserConstant.CAPTCHA));
        signUpRegistrationUserFormDto.setImageDto(getAvatarImageDto(request));
        return signUpRegistrationUserFormDto;
    }

    private ImageDto getAvatarImageDto(HttpServletRequest request) throws IOException, ServletException {
        String uploadAvatarDirectory = request.getServletContext().getInitParameter(ServletContextConstant.UPLOAD_AVATAR_DIRECTORY);
        ImageDto imageDto = new ImageDto();
        Part filePart = request.getPart(UserConstant.AVATAR);
        imageDto.setFileName(getSubmittedFileName(filePart));
        UUID uuid = UUID.randomUUID();
        String type = Paths.get(filePart.getContentType()).getFileName().toString();
        imageDto.setHashFileName(uuid.toString().concat(DOT).concat(type));
        imageDto.setLink(uploadAvatarDirectory + imageDto.getHashFileName());
        return imageDto;
    }

    private String getSubmittedFileName(Part part) {
        String[] partHeaders = part.getHeader("content-disposition").split(";");
        return Arrays.stream(partHeaders)
                .filter(header -> header.trim().startsWith("filename"))
                .map(this::getFileName)
                .findFirst()
                .orElse(StringUtils.EMPTY);
    }

    private String getFileName(String header) {
        String fileName = header.substring(header.indexOf('=') + 1).trim().replace("\"", StringUtils.EMPTY);
        return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
    }

    private boolean getReceivingNewsletter(HttpServletRequest request) {
        String parameter = request.getParameter(UserConstant.RECEIVING_NEWSLETTER);
        return Boolean.parseBoolean(parameter);
    }

    public SignInUserFormDto getSignInDto(HttpServletRequest request) {
        SignInUserFormDto signInUserFormDto = new SignInUserFormDto();
        signInUserFormDto.setLogin(request.getParameter(UserConstant.INPUT_LOGIN));
        signInUserFormDto.setPassword(request.getParameter(UserConstant.INPUT_PASSWORD));
        return signInUserFormDto;
    }

    public ProductFilterDto getFilterDto(HttpServletRequest request) {
        ProductFilterDto productFilterDto = new ProductFilterDto();
        productFilterDto.setCategories(getCategories(request));
        productFilterDto.setProducers(getProducers(request));
        productFilterDto.setPrice(getPrices(request));
        productFilterDto.setSort(getStringParameter(request, RequestAttributeConstant.SORT_PARAMETER));
        productFilterDto.setSearchInformation(getStringParameter(request, RequestAttributeConstant.SEARCH_ITEM));
        productFilterDto.setPage(getPageParameter(request));
        productFilterDto.setAmountItem(getAmountItemParameter(request));
        return productFilterDto;
    }

    public AddressDto getAddressDto(HttpServletRequest request) throws IOException {
        Gson gson = new Gson();
        AddressDto addressDto = new AddressDto();
        JsonObject addressObject = gson.fromJson(request.getReader(), JsonObject.class);
        addressDto.setCountry(addressObject.get(RequestAttributeConstant.COUNTRY).getAsString());
        addressDto.setCity(addressObject.get(RequestAttributeConstant.CITY).getAsString());
        addressDto.setStreet(addressObject.get(RequestAttributeConstant.STREET).getAsString());
        addressDto.setFloor(getIntegerAddressParameter(addressObject, RequestAttributeConstant.FLOOR));
        addressDto.setPost(getIntegerAddressParameter(addressObject, RequestAttributeConstant.POST));
        addressDto.setHouseNumber(getIntegerAddressParameter(addressObject, RequestAttributeConstant.HOUSE_NUMBER));
        return addressDto;
    }

    private int getIntegerAddressParameter(JsonObject addressObject, String title) {
        String stringNumber = addressObject.get(title).getAsString();
        if (isValidNumber(stringNumber, REGEXP_INTEGER_NUMBER)) {
            return Integer.parseInt(stringNumber);
        }
        return -1;
    }

    private List<String> getCategories(HttpServletRequest request) {
        String[] producers = request.getParameterValues(RequestAttributeConstant.CATEGORY);
        return Objects.isNull(producers) ? new ArrayList<>() : Arrays.asList(producers);
    }

    private List<String> getProducers(HttpServletRequest request) {
        String[] producers = request.getParameterValues(RequestAttributeConstant.PRODUCER);
        return Objects.isNull(producers) ? new ArrayList<>() : Arrays.asList(producers);
    }

    private List<Double> getPrices(HttpServletRequest request) {
        List<Double> prices = new ArrayList<>();
        checkStartPrice(request, prices);
        checkEndPrice(request, prices);
        return prices;
    }

    private void checkStartPrice(HttpServletRequest request, List<Double> prices) {
        String stringPrice = request.getParameter(RequestAttributeConstant.START_PRICE);
        if (isValidNumber(stringPrice, REGEXP_DOUBLE_NUMBER)) {
            prices.add(Double.parseDouble(stringPrice));
        } else {
            prices.add(ZERO);
        }
    }

    private void checkEndPrice(HttpServletRequest request, List<Double> prices) {
        String stringPrice = request.getParameter(RequestAttributeConstant.END_PRICE);
        if (isValidNumber(stringPrice, REGEXP_DOUBLE_NUMBER)) {
            prices.add(Double.parseDouble(stringPrice));
        }
    }

    private String getStringParameter(HttpServletRequest request, String title) {
        return request.getParameter(title);
    }

    private int getPageParameter(HttpServletRequest request) {
        String stringNumber = request.getParameter(RequestAttributeConstant.PAGES);
        if (isValidNumber(stringNumber, REGEXP_INTEGER_NUMBER)) {
            return Integer.parseInt(request.getParameter(RequestAttributeConstant.PAGES));
        } else {
            return ONE;
        }
    }

    private int getAmountItemParameter(HttpServletRequest request) {
        String stringNumber = request.getParameter(RequestAttributeConstant.ITEMS_AMOUNT);
        if (isValidNumber(stringNumber, REGEXP_INTEGER_NUMBER)) {
            int itemAmount = Integer.parseInt(request.getParameter(RequestAttributeConstant.ITEMS_AMOUNT));
            return checkAmountItem(itemAmount);
        } else {
            return MIN_ITEM_AMOUNT;
        }
    }

    private int checkAmountItem(int itemAmount) {
        if (itemAmount >= MIN_ITEM_AMOUNT && itemAmount <= MAX_ITEM_AMOUNT) {
            return itemAmount;
        }
        return MIN_ITEM_AMOUNT;
    }

    private boolean isValidNumber(String number, String regexp) {
        return number.matches(regexp);
    }

}