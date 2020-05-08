$(document).ready(function () {
    $("#inputCountry").change(function () {
        validateStringWithoutSymbols("inputCountry");
    });
    $("#inputCity").change(function () {
        validateStringWithoutSymbols("inputCity");
    });
    $("#inputStreet").change(function () {
        validateStringWithoutSymbols("inputStreet");
    });
    $("#inputFloor").change(function () {
        validateNumberInput("inputFloor");
    });
    $("#inputPost").change(function () {
        validateNumberInput("inputPost");
    });
    $("#inputHouseNumber").change(function () {
        validateNumberInput("inputHouseNumber");
    });
});