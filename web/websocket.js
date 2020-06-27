let ws;

function connect() {
    let host = document.location.host;
    let pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host + pathname + "room");

    ws.onmessage = function (event) {
        let lamp = JSON.parse(event.data);
        let button = document.getElementById("button-id");
        button.innerHTML = lamp.state;
        document.getElementById("img-id").src = "img/" + lamp.state + ".png";
        if (lamp.state.localeCompare("On") == 0) {
            document.body.style.backgroundColor = "white";
        } else {
            document.body.style.backgroundColor = "black";
        }
    };
}

function send() {
    let state = document.getElementById("button-id").innerHTML;
    let json = JSON.stringify({
        "state": state
    });
    ws.send(json);
}