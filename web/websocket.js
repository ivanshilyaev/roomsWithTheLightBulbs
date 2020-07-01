let ws;

window.onload = function () {
    const host = document.location.host;
    const pathname = document.location.pathname;

    const roomId = document.getElementById("p-roomId").innerText;

    ws = new WebSocket("ws://" + host + pathname + "/" + roomId);

    ws.onmessage = function (event) {
        const lamp = JSON.parse(event.data);
        const button = document.getElementById("button-id");
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

    document.getElementById("button-id").onclick = function () {
        const state = document.getElementById("button-id").innerHTML;
        const json = JSON.stringify({
            "state": state
        });
        ws.send(json);
    };
};