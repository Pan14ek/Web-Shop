package com.test.makieiev.webshop.util.filter.impl;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import com.test.makieiev.webshop.util.filter.Filter;
import org.apache.commons.lang3.StringUtils;

public class PageFilter extends Filter {

    @Override
    protected boolean isFilter(ProductFilterDto productFilterDto) {
        return productFilterDto.getPage() > 0;
    }

    @Override
    protected void updateQuery(StringBuilder query, ProductFilterDto productFilterDto) {
        StringBuilder startQuery = query.append(StringUtils.SPACE).append(FilterConstant.OFFSET);
        if (productFilterDto.getAmountItem() != 1) {
            startQuery.append((productFilterDto.getPage() - 1) * productFilterDto.getAmountItem());
        }
    }

}