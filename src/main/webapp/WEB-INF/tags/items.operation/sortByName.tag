<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty filters}">
        <select id="sortItemsByName" class="form-control form-control-sm" name="sortName">
            <c:choose>
                <c:when test="${filters.nameSort == 'ascName'}">
                    <option value="ascName" selected>A-Z</option>
                    <option value="descName">Z-A</option>
                </c:when>
                <c:otherwise>
                    <option value="ascName">A-Z</option>
                    <option value="descName" selected>Z-A</option>
                </c:otherwise>
            </c:choose>
        </select>
    </c:when>
    <c:otherwise>
        <select id="sortItemsByName" class="form-control form-control-sm" name="sortName">
            <option value="ascName">A-Z</option>
            <option value="descName">Z-A</option>
        </select>
    </c:otherwise>
</c:choose>