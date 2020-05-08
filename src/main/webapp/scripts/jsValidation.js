const EMPTY = "";
const REGEXP_EMAIL = "(.+@(\\w*\\W*\\w.*))";
const REGEXP_STRING_WITHOUT_SYMBOLS = "^[A-Za-z]+$";

function validateString(inputId) {
    let inputField = document.getElementById(inputId);
    if (Object.is(inputField.value, EMPTY)) {
        invalidField(inputId, "Field is valid!");
        return false;
    } else {
        validField(inputId, "Field is valid!");
        return true;
    }
}

function validateStringWithoutSymbols(inputId) {
    let inputField = document.getElementById(inputId);
    let value = inputField.value;
    if (!Object.is(value, EMPTY)) {
        let regexp = new RegExp(REGEXP_STRING_WITHOUT_SYMBOLS);
        let flag = regexp.test(value);
        if (flag) {
            validField(input, "Field is valid");
            return true;
        }
    }
    invalidField(input, "Write correct information");
    return false;
}

function validatePassword(inputId) {
    let inputField = document.getElementById(inputId);
    let value = inputField.value;
    if (!Object.is(value, EMPTY) && value.length >= 6) {
        validField(input, "Password is valid");
        return true;
    }
    invalidField(input, "Write correct password");
    return false;
}

function checkRepeatPassword(passwordId, repeatPasswordId) {
    let inputPasswordField = document.getElementById(passwordId);
    let inputRepeatPasswordField = document.getElementById(repeatPasswordId);
    let passwordValue = inputPasswordField.value;
    let repeatPasswordValue = inputRepeatPasswordField.value;
    if (!Object.is(repeatPasswordValue, EMPTY) && Object.is(passwordValue, passwordRepeatValue)) {
        validField(passwordInput, "Password is valid");
        validField(passwordRepeatInput, "Password repeat is valid");
        return true;
    }
    invalidField(passwordRepeatInput, "Repeat password is not equel");
    return false;
}

function validateEmail(inputId) {
    let inputField = document.getElementById(inputId);
    let email = inputField.value;
    if (!Object.is(email, "")) {
        let regexp = new RegExp("(.+@(\\w*\\W*\\w.*))");
        let flag = regexp.test(email);
        if (flag) {
            validField(inputId, "Email is valid!");
            return true;
        }
    }
    invalidField(inputId, "Email is incorrect ! Write correct Email .")
    return false;
}

function checkSignInForm(email, password) {
    let valid = true;
    let parameters = [validateEmail(email), validateString(password)];
    parameters.forEach(function (parameter, i, parameters) {
        if (!parameter) {
            valid = false;
        }
    })
    return valid;
}

function checkRegistrationForm(firstName, lastName, login, email, password, repeatPassword) {
    let valid = true;
    let parameters = [validateStringWithoutSymbols(firstName),
        validateStringWithoutSymbols(lastName),
        validateString(login),
        validatePassword(password),
        validateEmail(email),
        checkRepeatPassword(password, repeatPassword)];
    parameters.forEach(function (parameter, i, parameters) {
        if (!parameter) {
            valid = false;
        }
    })
    return valid;
}

function validField(input, message) {
    let inputField = document.getElementById(input);
    inputField.setAttribute("class", "form-control mb-2 is-valid");
    inputField.style.border = "2px solid green";
    inputField.setAttribute("title", message);
}

function invalidField(input, message) {
    let inputField = document.getElementById(input);
    inputField.setAttribute("class", "form-control mb-2 is-invalid");
    inputField.style.border = "2px solid red";
    inputField.setAttribute("title", message);
}