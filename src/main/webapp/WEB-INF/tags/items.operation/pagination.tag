<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<label for="pages">Pages</label>
<c:choose>
    <c:when test="${empty filters}">
        <select id="pages" class="form-control form-control-sm" name="pages">
            <c:forEach var="i" begin="1" end="${pageNumbers}">
                <option value="${i}">${i}</option>
            </c:forEach>
        </select>
    </c:when>
    <c:otherwise>
        <select id="pages" class="form-control form-control-sm" name="pages">
            <c:forEach var="i" begin="1" end="${pageNumbers}">
                <c:choose>
                    <c:when test="${filters.page == i}">
                        <option value="${i}" selected>${i}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${i}">${i}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </c:otherwise>
</c:choose>