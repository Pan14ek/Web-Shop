<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="order" tagdir="/WEB-INF/tags/order" %>
<div class="container">
    <c:if test="${lowPriority}">
        <div class="alert alert-danger" role="alert">
            You have a low priority.
        </div>
        <c:remove var="lowPriority"/>
    </c:if>
    <c:if test="${notAuthorized}">
        <div class="alert alert-info" role="alert">
            Please, sign in or <a href="/signup" class="alert-link">sign up</a> !
        </div>
        <c:remove var="notAuthorized"/>
    </c:if>
    <c:if test="${noItems}">
        <div class="alert alert-info" role="alert">
            You have not items in your cart !
        </div>
        <c:remove var="noItems"/>
    </c:if>
    <h3>Your cart</h3>
    <c:choose>
        <c:when test="${not empty basket.items}">
            <order:basket/>
        </c:when>
        <c:otherwise>
            <h2>Your basket is empty</h2>
        </c:otherwise>
    </c:choose>
</div>