<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="shopProducer" items="${producers}">
    <c:set var="flag" value="false"/>
    <div>
        <c:if test="${not empty filters}">
            <c:forEach var="attributeProducers" items="${filters.producers}">
                <c:if test="${shopProducer.title == attributeProducers}">
                    <c:set var="flag" value="true"/>
                </c:if>
            </c:forEach>
        </c:if>
        <input type="checkbox" id="producer" name="producer"
               value="${shopProducer.title}" ${flag==true ? 'checked' : ''}>
        <label for="producer">${shopProducer.title}</label>
    </div>
</c:forEach>