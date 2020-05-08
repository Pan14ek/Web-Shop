package com.test.makieiev.webshop.util.validation;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductFilterValidator {

    private static final String REGEXP_SORT_PARAMETER = "lowPrice|highPrice|ascName|descName";

    public void getProductFilterErrors(ProductFilterDto productFilterDto, Map<String, Boolean> errors) {
        validateCategories(productFilterDto.getCategories(), errors);
        validateProducers(productFilterDto.getProducers(), errors);
        validatePrice(productFilterDto.getPrice(), errors);
        validateSortParameter(productFilterDto.getSort(), errors);
        validatePage(productFilterDto.getPage(), errors);
        validateAmountItem(productFilterDto.getAmountItem(), errors);
    }

    private void validateCategories(List<String> categories, Map<String, Boolean> errors) {
        boolean isValid = categories.stream().allMatch(this::isValidLine);
        if (!isValid) {
            errors.put("category", true);
        }
    }

    private void validateProducers(List<String> producers, Map<String, Boolean> errors) {
        boolean isValid = producers.stream().allMatch(this::isValidLine);
        if (!isValid) {
            errors.put("producers", true);
        }
    }

    private void validatePrice(List<Double> prices, Map<String, Boolean> errors) {
        boolean isValidPrices = prices.stream().allMatch(this::isValidDoublePositiveNumber);
        if (!isValidPrices) {
            errors.put("price", true);
        }
    }

    private void validateSortParameter(String priceSort, Map<String, Boolean> errors) {
        if (!isValidSortParameter(priceSort)) {
            errors.put("sortParameter", true);
        }
    }

    private void validatePage(int page, Map<String, Boolean> errors) {
        if (!isValidPositiveNumber(page)) {
            errors.put("page", true);
        }
    }

    private void validateAmountItem(int amountItem, Map<String, Boolean> errors) {
        if (!isValidPositiveNumber(amountItem)) {
            errors.put("amountItem", true);
        }
    }

    private boolean isValidPositiveNumber(int number) {
        return number >= 0;
    }

    private boolean isValidDoublePositiveNumber(double number) {
        return number >= 0;
    }

    private boolean isValidLine(String line) {
        return !Objects.isNull(line) && !line.isEmpty();
    }

    private boolean isValidSortParameter(String sortParameter) {
        return sortParameter.matches(REGEXP_SORT_PARAMETER);
    }

}