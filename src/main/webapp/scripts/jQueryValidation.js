const LATTICE = "#";
const EMPTY = "";
const REGEXP_EMAIL = "(.+@(\\w+\\W+\\w.*))";
const REGEXP_STRING_WITHOUT_SYMBOLS = "^[A-Za-z]+$";
const REGEXP_NUMBER_FIELD = "^\\d+";
const VALID_FIELD = "Field is valid";
const INVALID_FIELD = "Write correct information";
const VALID_PASSWORD = "Password is valid";
const INVALID_PASSWORD = "Write correct password";
const VALID_REPEAT_PASSWORD = "Password repeat is valid";
const INVALID_REPEAT_PASSWORD = "Repeat password is not equal";
const VALID_EMAIL = "Email is valid";
const INVALID_EMAIL = "Write correct Email";

function validateString(inputId) {
    let input = LATTICE + inputId;
    let value = $(input).val();
    if (!Object.is(value, EMPTY)) {
        validField(input, VALID_FIELD);
        return true;
    } else {
        invalidField(input, INVALID_FIELD);
        return false;
    }
}

function validateNumberInput(inputId) {
    let input = LATTICE + inputId;
    let value = $(input).val();
    let regexp = new RegExp(REGEXP_NUMBER_FIELD);
    if (!Object.is(value, EMPTY) && regexp.test(value)) {
        validField(input, VALID_FIELD);
        return true;
    } else {
        invalidField(input, INVALID_FIELD);
        return false;
    }
}

function validateStringWithoutSymbols(inputId) {
    let input = LATTICE + inputId;
    let value = $(input).val();
    if (!Object.is(value, EMPTY)) {
        let regexp = new RegExp(REGEXP_STRING_WITHOUT_SYMBOLS);
        let flag = regexp.test(value);
        if (flag) {
            validField(input, VALID_FIELD);
            return true;
        }
    }
    invalidField(input, INVALID_FIELD);
    return false;
}

function validatePassword(inputId) {
    let input = LATTICE + inputId;
    let value = $(input).val();
    if (!Object.is(value, EMPTY) && value.length >= 6) {
        validField(input, VALID_PASSWORD);
        return true;
    }
    invalidField(input, INVALID_PASSWORD);
    return false;
}

function checkRepeatPassword(passwordId, repeatPasswordId) {
    let passwordInput = LATTICE + passwordId;
    let passwordRepeatInput = LATTICE + repeatPasswordId;
    let passwordValue = $(passwordInput).val();
    let passwordRepeatValue = $(passwordRepeatInput).val();
    if (!Object.is(passwordRepeatValue, EMPTY) && Object.is(passwordValue, passwordRepeatValue) && passwordRepeatValue.length >= 6 && passwordValue.length >= 6) {
        validField(passwordInput, VALID_PASSWORD);
        validField(passwordRepeatInput, VALID_REPEAT_PASSWORD);
        return true;
    }
    invalidField(passwordRepeatInput, INVALID_REPEAT_PASSWORD);
    return false;
}

function validateEmail(inputId) {
    let input = LATTICE + inputId;
    let email = $(input).val();
    if (!Object.is(email, EMPTY)) {
        let regexp = new RegExp(REGEXP_EMAIL);
        let flag = regexp.test(email.toLowerCase());
        if (flag) {
            validField(input, VALID_EMAIL);
            return true;
        }
    }
    invalidField(input, INVALID_EMAIL);
    return false;
}

function checkSignInForm(login, password) {
    let valid = true;
    let parameters = [validateString(login), validateString(password)];
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
    $(input).attr({
        "class": "form-control mb-2 is-valid",
        "title": message
    })
    $(input).css("border", "2px solid green");
}

function invalidField(input, message) {
    $(input).attr({
        "class": "form-control mb-2 is-invalid",
        "title": message
    });
    $(input).css("border", "2px solid red");
}