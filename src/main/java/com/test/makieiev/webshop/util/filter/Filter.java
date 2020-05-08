package com.test.makieiev.webshop.util.filter;

import com.test.makieiev.webshop.model.dto.ProductFilterDto;
import com.test.makieiev.webshop.util.constant.FilterConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * The filter class is responsible for query building
 * Heirs classes implemented realizing particular query building
 *
 * @author Oleksii_Makieiev1
 */
public abstract class Filter {

    private Filter next;

    public Filter() {
    }

    /**
     * Method activate filter .
     *
     * @return filtered list which filtered
     */
    public StringBuilder doFilter(StringBuilder query, ProductFilterDto productFilterDto) {
        if (isFilter(productFilterDto)) {
            updateQuery(query, productFilterDto);
        }
        return Objects.isNull(next) ? query : next.doFilter(query, productFilterDto);
    }

    /**
     * Each filter has special condition .
     *
     * @param productFilterDto is dto with special fields for checking information
     * @return true if file passed the condition
     */
    protected abstract boolean isFilter(ProductFilterDto productFilterDto);

    /**
     * @param query            is sql string
     * @param productFilterDto is dto with special fields for checking information
     */
    protected abstract void updateQuery(StringBuilder query, ProductFilterDto productFilterDto);

    /**
     * @param name     is name of parameter for query part
     * @param elements are elements with special parameters
     * @return StringBuilder with parameters
     */
    protected StringBuilder getResult(String name, List<String> elements) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name)
                .append(StringUtils.SPACE)
                .append(FilterConstant.IN)
                .append(StringUtils.SPACE)
                .append(FilterConstant.RIGHT_ROUND_BRACKET);
        concatData(stringBuilder, elements);
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(FilterConstant.COMMA));
        return stringBuilder.append(FilterConstant.LEFT_ROUND_BRACKET);
    }

    /**
     * Set next filter
     *
     * @param next is next filter
     */
    public void setNext(Filter next) {
        this.next = next;
    }

    /**
     * @param stringBuilder is StringBuilder with query
     * @param elements      are list with parameters
     */
    private void concatData(StringBuilder stringBuilder, List<String> elements) {
        for (String element : elements) {
            stringBuilder.append(FilterConstant.SINGLE_MARK).append(element).append(FilterConstant.SINGLE_MARK).append(FilterConstant.COMMA);
        }
    }

}