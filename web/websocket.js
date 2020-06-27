let ws;

function connect() {
    let host = document.location.host;
    let pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host + pathname + "room");

    ws.onmessage = function (event) {
        let lamp = JSON.parse(event.data);
        let button = document.getElementById("button");
        button.innerHTML = lamp.state;
    };
}

function send() {
    let state = document.getElementById("button").innerHTML;
    let json = JSON.stringify({
        "state": state
    });
    ws.send(json);
}