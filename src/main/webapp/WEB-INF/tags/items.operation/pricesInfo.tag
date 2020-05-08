<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty filters}">
        <label for="startPrice">Start price</label>
        <input type="number" id="startPrice" name="startPrice" min="0"
               value="${not empty filters.price ? filters.price[0] : 0 }">
        <label for="endPrice">End price</label>
        <input type="number" id="endPrice" name="endPrice" min="0"
               value="${filters.price.size() eq 2 ? filters.price[1] : '' }">
    </c:when>
    <c:otherwise>
        <label for="startPrice">Start price</label>
        <input type="number" id="startPrice" name="startPrice" min="0">
        <label for="endPrice">End price</label>
        <input type="number" id="endPrice" name="endPrice" min="0">
    </c:otherwise>
</c:choose>