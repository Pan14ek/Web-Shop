<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<label for="pages">Amount items</label>
<select id="itemsAmount" class="form-control form-control-sm" name="itemsAmount">
    <c:choose>
        <c:when test="${not empty filters}">
            <c:choose>
                <c:when test="${filters.amountItem == 6}">
                    <option value="6" selected>6</option>
                    <option value="12">12</option>
                    <option value="24">24</option>
                </c:when>
                <c:when test="${filters.amountItem == 12}">
                    <option value="6">6</option>
                    <option value="12" selected>12</option>
                    <option value="24">24</option>
                </c:when>
                <c:otherwise>
                    <option value="6">6</option>
                    <option value="12">12</option>
                    <option value="24" selected>24</option>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <option value="6">6</option>
            <option value="12">12</option>
            <option value="24">24</option>
        </c:otherwise>
    </c:choose>
</select>