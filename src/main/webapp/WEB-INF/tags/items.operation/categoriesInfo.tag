<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="shopCategory" items="${categories}">
    <c:set var="flag" value="false"/>
    <div>
        <c:if test="${not empty filters}">
            <c:forEach var="attributeCategory" items="${filters.categories}">
                <c:if test="${shopCategory.title == attributeCategory}">
                    <c:set var="flag" value="true"/>
                </c:if>
            </c:forEach>
        </c:if>
        <input type="checkbox" id="category" name="category"
               value="${shopCategory.title}" ${flag==true ? 'checked' : ''}>
        <label for="category">${shopCategory.title}</label>
    </div>
</c:forEach>