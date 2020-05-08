<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="captcha" uri="/WEB-INF/tags/captcha.tld" %>
<br>
<div class="container">
    <c:if test="${lowPriority}">
        <div class="alert alert-danger" role="alert">
            You have a low priority.
        </div>
        <c:remove var="lowPriority"/>
    </c:if>
    <h4>Registration</h4>
    <div>
        <form action="signup" method="post" name="registration" enctype="multipart/form-data" novalidate>
            <div class="form-group row">
                <label for="firstName" class="col-sm-2 col-form-label">First name</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control mb-2" id="firstName" placeholder="First name"
                           name="firstName" value="${userTemporary.firstName}">
                    <c:if test="${errors['firstName']}">
                        <br>
                        <p id="warning">Write correct first name ! First name should be without numbers</p>
                    </c:if>
                </div>
            </div>
            <div class="form-group row">
                <label for="lastName" class="col-sm-2 col-form-label">Last name</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control mb-2" id="lastName" placeholder="Last name" name="lastName"
                           value="${userTemporary.lastName}">
                    <c:if test="${errors['lastName']}">
                        <br>
                        <p id="warning">Write correct last name ! Last name should be without numbers</p>
                    </c:if>
                </div>
            </div>
            <div class="form-group row">
                <label for="login" class="col-sm-2 col-form-label">Login</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control mb-2" id="login" placeholder="Login" name="login"
                           value="${userTemporary.login}">
                    <c:if test="${errors['login']}">
                        <br>
                        <p id="warning">Login is empty! Write login</p>
                    </c:if>

                    <c:if test="${errors['employedLogin']}">
                        <br>
                        <p id="warning">Login is employed! Write new login</p>
                    </c:if>
                </div>
            </div>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-4">
                    <input type="email" class="form-control mb-2" id="email" placeholder="Email" name="email"
                           value="${userTemporary.email}">
                    <c:if test="${errors['email']}">
                        <br>
                        <p id="warning">Write correct email!</p>
                    </c:if>
                </div>
            </div>
            <div class="form-group row">
                <label for="password" class="col-sm-2 col-form-label">Password</label>
                <div class="col-sm-4">
                    <input type="password" class="form-control mb-2" id="password" placeholder="Password"
                           name="password">
                    <small id="password" class="form-text text-muted">The password should have more than 6
                        symbols</small>
                    <c:if test="${errors['password']}">
                        <br>
                        <p id="warning">Write correct password!</p>
                    </c:if>
                </div>
            </div>
            <div class="form-group row">
                <label for="repeatPassword" class="col-sm-2 col-form-label">Repeat password</label>
                <div class="col-sm-4">
                    <input type="password" class="form-control mb-2" id="repeatPassword" placeholder="Password"
                           name="repeatPassword">
                    <c:if test="${errors['repeatPassword']}">
                        <br>
                        <p id="warning">Repeat password is not equal password! Check your password !</p>
                    </c:if>
                </div>
            </div>
            <div class="form-group row">
                <label for="avatar" class="col-sm-2 col-form-label">Avatar</label>
                <div class="col-sm-4">
                    <input type="file" class="form-control-file mb-2" id="avatar" name="avatar">
                </div>
            </div>

            <div class="form-group row">
                <captcha:img/>
                <div class="col-sm-4">
                    <input type="text" class="form-control mb-2" id="userWord" placeholder="Write captcha"
                           name="userWord">
                </div>
            </div>
            <c:if test="${errors['captchaError']}">
                <p id="warning">Captcha is not valid!</p>
            </c:if>
            <c:if test="${errors['time']}">
                <p id="warning">Time is out!</p>
            </c:if>
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="receiveMessage" name="receivingNewsletter"
                       value="true">
                <label class="form-check-label" for="receiveMessage">Do you want get news ?</label>
            </div>
            <br>
            <button class="btn btn-primary" type="submit">Sign up</button>
            <button class="btn btn-secondary" type="button">Back</button>
        </form>
    </div>
</div>