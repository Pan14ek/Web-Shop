<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty filters}">
        <select id="sortItemsByPrice" class="form-control form-control-sm" name="sortPrice">
            <c:choose>
                <c:when test="${filters.priceSort == 'lowPrice'}">
                    <option value="lowPrice" selected>Lower price</option>
                    <option value="highPrice">High price</option>
                </c:when>
                <c:otherwise>
                    <option value="lowPrice">Lower price</option>
                    <option value="highPrice" selected>High price</option>
                </c:otherwise>
            </c:choose>
        </select>
    </c:when>
    <c:otherwise>
        <select id="sortItemsByPrice" class="form-control form-control-sm" name="sortPrice">
            <option value="lowPrice">Lower price</option>
            <option value="highPrice">High price</option>
        </select>
    </c:otherwise>
</c:choose>