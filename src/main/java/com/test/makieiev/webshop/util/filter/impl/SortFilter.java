package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class SortFilter extends Filter {

    private static final String NAME = "Name";

    @Override
    protected boolean isFilter(ProductFilterDto productFilterDto) {
        return Objects.nonNull(productFilterDto.getSort());
    }

    @Override
    protected void updateQuery(StringBuilder query, ProductFilterDto productFilterDto) {
        if (productFilterDto.getSort().contains(NAME)) {
            query.append(StringUtils.SPACE)
                    .append(FilterConstant.ORDER_BY_ELECTRONIC_NAME)
                    .append(getSortType(productFilterDto));
        } else {
            query.append(StringUtils.SPACE)
                    .append(FilterConstant.ORDER_BY_PRICE)
                    .append(getSortType(productFilterDto));
        }
    }

    private String getSortType(ProductFilterDto productFilterDto) {
        if (productFilterDto.getSort().contains(NAME)) {
            return getSortNameType(productFilterDto);
        }
        return getSortPriceType(productFilterDto);
    }

    private String getSortNameType(ProductFilterDto productFilterDto) {
        if (Objects.equals(FilterConstant.ASC_NAME, productFilterDto.getSort())) {
            return FilterConstant.ASC;
        }
        return FilterConstant.DESC;
    }

    private String getSortPriceType(ProductFilterDto productFilterDto) {
        if (Objects.equals(FilterConstant.LOW_PRICE, productFilterDto.getSort())) {
            return FilterConstant.ASC;
        }
        return FilterConstant.DESC;
    }

}