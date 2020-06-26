let ws;

function connect() {
    let host = document.location.host;
    let pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host + pathname + "room");

    ws.onmessage = function (event) {
        let lamp = JSON.parse(event.data);
        $("#button").html(lamp.state);
    };
}

function send() {
    let state = $("#button").text();
    let json = JSON.stringify({
        "state": state
    });
    ws.send(json);
}