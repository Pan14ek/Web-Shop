$(document).ready(function () {
    $("#orderButton").click(function () {
        $.ajax({
            type: "POST",
            url: "/order/operation",
            contentType: "application/json",
            data: JSON.stringify(getAddress()),
            success: function (data) {
                inputError(data);
            }
        });
    });
});

function inputError(list) {
    if (Object.is(list, true)) {
        $(location).attr('href', '/index');
    }
    for (let element in list) {
        let input = "#" + element;
        invalidField(input, "Write correct field");
    }
}

function getAddress() {
    return {
        "country": $("#inputCountry").val(),
        "city": $("#inputCity").val(),
        "street": $("#inputStreet").val(),
        "floor": $("#inputFloor").val(),
        "post": $("#inputPost").val(),
        "houseNumber": $("#inputHouseNumber").val()
    }
}