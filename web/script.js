$(document).on("click", "#button", function () {
    let data = {
        "button": $("#button").text()
    };
    $.ajax({
        type: "POST",
        url: "Controller",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            $("#button").html(response.lamp);
        }
    });
});