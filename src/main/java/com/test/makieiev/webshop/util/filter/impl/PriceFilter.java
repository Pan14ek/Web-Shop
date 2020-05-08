package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PriceFilter extends Filter {

    @Override
    protected boolean isFilter(ProductFilterDto productFilterDto) {
        return !productFilterDto.getPrice().isEmpty();
    }

    @Override
    protected void updateQuery(StringBuilder query, ProductFilterDto productFilterDto) {
        if (query.toString().contains(FilterConstant.WHERE)) {
            query.append(StringUtils.SPACE)
                    .append(FilterConstant.AND)
                    .append(StringUtils.SPACE)
                    .append(getPriceQuery(productFilterDto));
        } else {
            query.append(FilterConstant.WHERE)
                    .append(StringUtils.SPACE)
                    .append(getPriceQuery(productFilterDto));
        }
    }

    private String getPriceQuery(ProductFilterDto productFilterDto) {
        List<Double> prices = productFilterDto.getPrice();
        if (prices.size() == 1) {
            return FilterConstant.ELECTRONICS_PRICE_FOR_LOW_PRICE + prices.get(0);
        }
        return FilterConstant.ELECTRONICS_PRICE_BETWEEN + prices.get(0) + FilterConstant.AND_WITH_SPACES + prices.get(1);
    }

}