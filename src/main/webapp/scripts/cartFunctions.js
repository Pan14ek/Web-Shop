function minusQuantity(elementId, inputId, priceId, totalPriceId) {
    let input = "#" + inputId;
    let amount = $(input).val();
    if (amount > 1) {
        let updatedAmount = parseInt(amount, 10) - 1;
        $(input).val(updatedAmount);
        calculateTotalPriceButtons(elementId, priceId, inputId, totalPriceId, updatedAmount);
    }
}

function plusQuantity(elementId, inputId, priceId, totalPriceId) {
    let input = "#" + inputId;
    let amount = $(input).val();
    let updatedAmount = parseInt(amount, 10) + 1;
    $(input).val(updatedAmount);
    calculateTotalPriceButtons(elementId, priceId, inputId, totalPriceId, updatedAmount);
}

function calculateTotalPriceButtons(elementId, priceId, quantityId, totalPriceId, updatedAmount) {
    let price = $("#" + priceId).html();
    let amount = $("#" + quantityId).val();
    updateBasket(elementId, updatedAmount);
    let totalPrice = parseInt(price * amount, 10);
    $("#" + totalPriceId).val(totalPrice);
}

function calculateTotalPrice(elementId, priceId, quantityId, totalPriceId) {
    let price = $("#" + priceId).html();
    let amount = $("#" + quantityId).val();
    updateBasket(elementId, amount);
    let totalPrice = parseInt(price * amount, 10);
    $("#" + totalPriceId).val(totalPrice);
}

function updateBasket(elementId, amount) {
    $.ajax({
        type: "PUT",
        url: "/basket",
        contentType: "application/json",
        data: JSON.stringify({
            "id": elementId,
            "amount": amount
        })
    });
}

$(document).ready(function () {
    $(".cancelButton").click(function () {
        $.ajax({
            type: 'DELETE',
            url: '/basket' + '?id=' + $(this).attr("value")
        });
        $(this).closest('tr').remove();
    });
});

$(document).ready(function () {
    $("#checkout").click(function () {
        $(location).attr('href', '/order');
    });
});