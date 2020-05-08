package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class SearchFilter extends Filter {

    @Override
    protected boolean isFilter(ProductFilterDto productFilterDto) {
        return Objects.nonNull(productFilterDto.getSearchInformation()) && productFilterDto.getSearchInformation().length() >= 1;
    }

    @Override
    protected void updateQuery(StringBuilder query, ProductFilterDto productFilterDto) {
        if (query.toString().contains(FilterConstant.WHERE)) {
            query.append(StringUtils.SPACE).append(FilterConstant.AND).append(StringUtils.SPACE).append(getSearchQuery(productFilterDto));
        } else {
            query.append(FilterConstant.WHERE).append(StringUtils.SPACE).append(getSearchQuery(productFilterDto));
        }
    }

    private String getSearchQuery(ProductFilterDto productFilterDto) {
        return FilterConstant.ELECTRONICS_ELECTRONIC_NAME + FilterConstant.LIKE + FilterConstant.PERCENT_SIGN_RIGHT + productFilterDto.getSearchInformation() + FilterConstant.PERCENT_SIGN_LEFT;
    }

}