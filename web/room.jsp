<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Room</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="websocket.js"></script>
</head>
<body onload="connect();">
<img id="img-id" src="" alt="">
<p hidden id="p-roomId">${requestScope.roomId}</p>
<button id="button-id" onclick="send();"></button>
</body>
</html>
