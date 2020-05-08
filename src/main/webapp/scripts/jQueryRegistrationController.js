$(document).ready(function () {
    $("#firstName").change(function () {
        validateStringWithoutSymbols("firstName");
    });
    $("#lastName").change(function () {
        validateStringWithoutSymbols("lastName");
    });
    $("#login").change(function () {
        validateString("login");
    });
    $("#email").change(function () {
        validateEmail("email");
    });
    $("#password").change(function () {
        validatePassword("password");
    });
    $("#repeatPassword").change(function () {
        checkRepeatPassword('password', 'repeatPassword');
    });
    $("form[name=registration]").submit(function () {
        return checkRegistrationForm('firstName', 'lastName', 'login', 'email', 'password', 'repeatPassword');
    });
});

$(window).on('load', function () {
    let firstName = $("#firstName").val();
    let lastName = $("#lastName").val();
    let password = $("#password").val();
    let repeatPassword = $("#repeatPassword").val();
    let login = $("#login").val();
    let email = $("#email").val();
    if (!Object.is(firstName, "") && !Object.is(lastName, "") && !Object.is(login, "") && !Object.is(email, "")) {
        validateStringWithoutSymbols("firstName");
        validateStringWithoutSymbols("lastName");
        validateString("login");
        validateEmail("email");
    }
});