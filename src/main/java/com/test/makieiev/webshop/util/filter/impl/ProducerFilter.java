package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.apache.commons.lang3.StringUtils;

public class ProducerFilter extends Filter {

    @Override
    protected boolean isFilter(ProductFilterDto productFilterDto) {
        return !productFilterDto.getProducers().isEmpty();
    }

    @Override
    protected void updateQuery(StringBuilder query, ProductFilterDto productFilterDto) {
        if (query.toString().contains(FilterConstant.WHERE)) {
            query.append(StringUtils.SPACE).append(FilterConstant.AND).append(StringUtils.SPACE).append(getProducersQuery(productFilterDto));
        } else {
            query.append(FilterConstant.WHERE).append(StringUtils.SPACE).append(getProducersQuery(productFilterDto));
        }
    }

    private StringBuilder getProducersQuery(ProductFilterDto productFilterDto) {
        return getResult(FilterConstant.PRODUCERS_TITLE, productFilterDto.getProducers());
    }

}