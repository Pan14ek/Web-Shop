<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty language}">
        <fmt:setLocale value="${language}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${cookie[language]}"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="interface"/>
<c:choose>
    <c:when test="${user.firstName != null}">
        <li class="nav-item">
            <img width="100px" height="50px" src='avatar' alt='avatar'/>
        </li>
        <li class="nav-item">
            <user:welcome/>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="authorization"><fmt:message key="logOut"/></a>
        </li>
    </c:when>
    <c:otherwise>
        <li class="nav-item" data-toggle="modal" data-target="#modal-center">
            <a class="nav-link" href="#">Sign in <span class="sr-only">(current)</span></a>
        </li>
        <div class="modal fade" id="modal-center" tabindex="-1" role="dialog" aria-labelledby="modal-center-title"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modal-center-title"><fmt:message key="signIn"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="authorization" method="post" name="signIn">
                            <div class="form-group">
                                <label for="inputLogin"><fmt:message key="login"/></label>
                                <input type="text" class="form-control" id="inputLogin" name="inputLogin"
                                       placeholder="Enter login" value="${userAuthorizationTemporary.login}">
                                <c:if test="${signInErrors['incorrectLogin']}">
                                    <br>
                                    <p id="warning"><fmt:message key="incorrectLogin"/></p>
                                </c:if>
                                <c:if test="${signInErrors['login']}">
                                    <br>
                                    <p id="warning"><fmt:message key="emptyLogin"/></p>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword"><fmt:message key="password"/></label>
                                <input type="password" class="form-control" id="inputPassword" name="inputPassword"
                                       placeholder="Password" value="${userAuthorizationTemporary.password}">
                                <c:if test="${signInErrors['password']}">
                                    <br>
                                    <p id="warning">Write correct password!</p>
                                </c:if>
                                <c:if test="${signInErrors['incorrectPassword']}">
                                    <br>
                                    <p id="warning">Incorrect password ! Check your password!</p>
                                </c:if>
                            </div>
                            <button type="submit" class="btn btn-primary"><fmt:message key="signIn"/></button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <a class="nav-link" href="signup">
                            <button type="button" class="btn btn-primary"><fmt:message key="signUp"/></button>
                        </a>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                                key="close"/></button>
                    </div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>