<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="interface"/>
<%@taglib prefix="info" tagdir="/WEB-INF/tags" %>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark text-center" style="height: 50px">
    <div class="collapse navbar-collapse" id="navbarToggler1">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="#"><fmt:message key="myAccount"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="cart"><fmt:message key="myCart"/></a>
            </li>
            <c:if test="${not empty user}">
                <c:if test="${user.role.title == 'Administrator'}">
                    <li class="nav-item">
                        <a class="nav-link" href="admin"><fmt:message key="admin"/></a>
                    </li>
                </c:if>
            </c:if>
        </ul>
        <div class="my-lg-0">
            <ul class="navbar-nav mr-automt-lg-0">
                <info:locale/>
                <info:signin/>
            </ul>
        </div>
    </div>
</nav>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="collapse navbar-collapse" id="navbarToggler2">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="/index"><fmt:message key="home"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/items"><fmt:message key="shopPage"/></a>
            </li>
        </ul>
    </div>
</nav>