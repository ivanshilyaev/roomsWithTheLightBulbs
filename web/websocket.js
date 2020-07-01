let ws;

function connect() {
    let host = document.location.host;
    let pathname = document.location.pathname;

    let roomId = document.getElementById("p-roomId").innerText;

    ws = new WebSocket("ws://" + host + pathname + "/" + roomId);

    ws.onmessage = function (event) {
        let lamp = JSON.parse(event.data);
        let button = document.getElementById("button-id");
        if (lamp.state.localeCompare("On") === 0) {
            button.innerHTML = "On";
            document.getElementById("img-id").src = "img/On.png";
            document.body.style.backgroundColor = "white";
            document.body.style.color = "black";
        } else {
            button.innerHTML = "Off";
            document.getElementById("img-id").src = "img/Off.png";
            document.body.style.backgroundColor = "black";
            document.body.style.color = "white";
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