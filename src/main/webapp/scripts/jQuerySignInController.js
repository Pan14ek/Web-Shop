$(document).ready(function () {
    $("#inputLogin").change(function () {
        validateString("inputLogin");
    });
    $("#inputPassword").change(function () {
        validatePassword("inputPassword");
    });
    $("form[name=signIn]").submit(function () {
        return checkSignInForm('inputLogin', 'inputPassword');
    });
});