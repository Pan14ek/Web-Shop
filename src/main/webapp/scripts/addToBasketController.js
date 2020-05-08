$(document).ready(function () {
    $(".itemButton").click(function () {
        $.ajax({
            type: "POST",
            url: "/basket",
            contentType: "application/json",
            data: JSON.stringify({
                "id": $(this).attr("value"),
                "amount": 1
            })
        });
    });
});