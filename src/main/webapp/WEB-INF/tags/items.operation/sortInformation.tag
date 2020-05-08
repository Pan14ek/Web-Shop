<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty filters}">
        <select id="sortItemsByName" class="form-control form-control-sm" name="sortParameter">
            <c:choose>
                <c:when test="${filters.sort == 'ascName'}">
                    <option value="ascName" selected>A-Z</option>
                    <option value="descName">Z-A</option>
                    <option value="lowPrice">Lower price</option>
                    <option value="highPrice">High price</option>
                </c:when>
                <c:when test="${filters.sort == 'descName'}">
                    <option value="ascName">A-Z</option>
                    <option value="descName" selected>Z-A</option>
                    <option value="lowPrice">Lower price</option>
                    <option value="highPrice">High price</option>
                </c:when>
                <c:when test="${filters.sort == 'lowPrice'}">
                    <option value="ascName">A-Z</option>
                    <option value="descName">Z-A</option>
                    <option value="lowPrice" selected>Lower price</option>
                    <option value="highPrice">High price</option>
                </c:when>
                <c:otherwise>
                    <option value="ascName">A-Z</option>
                    <option value="descName">Z-A</option>
                    <option value="lowPrice">Lower price</option>
                    <option value="highPrice" selected>High price</option>
                </c:otherwise>
            </c:choose>
        </select>
    </c:when>
    <c:otherwise>
        <select id="sortItemsByName" class="form-control form-control-sm" name="sortParameter">
            <option value="ascName">A-Z</option>
            <option value="descName">Z-A</option>
            <option value="lowPrice">Lower price</option>
            <option value="highPrice">High price</option>
        </select>
    </c:otherwise>
</c:choose>