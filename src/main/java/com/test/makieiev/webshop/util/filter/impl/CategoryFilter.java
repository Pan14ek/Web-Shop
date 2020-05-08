package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.apache.commons.lang3.StringUtils;

public class CategoryFilter extends Filter {

    @Override
    protected boolean isFilter(ProductFilterDto productFilterDto) {
        return !productFilterDto.getCategories().isEmpty();
    }

    @Override
    protected void updateQuery(StringBuilder query, ProductFilterDto productFilterDto) {
        if (query.toString().contains(FilterConstant.WHERE)) {
            query.append(StringUtils.SPACE).append(FilterConstant.AND).append(StringUtils.SPACE).append(getCategoryQuery(productFilterDto));
        } else {
            query.append(FilterConstant.WHERE).append(StringUtils.SPACE).append(getCategoryQuery(productFilterDto));
        }
    }

    private StringBuilder getCategoryQuery(ProductFilterDto productFilterDto) {
        return getResult(FilterConstant.CATEGORIES_CATEGORY_NAME, productFilterDto.getCategories());
    }

}