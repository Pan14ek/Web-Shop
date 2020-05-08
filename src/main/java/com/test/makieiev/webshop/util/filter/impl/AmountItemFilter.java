package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.apache.commons.lang3.StringUtils;

public class AmountItemFilter extends Filter {

    @Override
    protected boolean isFilter(ProductFilterDto productFilterDto) {
        return productFilterDto.getAmountItem() > 0;
    }

    @Override
    protected void updateQuery(StringBuilder query, ProductFilterDto productFilterDto) {
        query.append(StringUtils.SPACE)
                .append(FilterConstant.LIMIT)
                .append(productFilterDto.getAmountItem());
    }

}