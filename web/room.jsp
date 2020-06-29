<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Room</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="websocket.js"></script>
</head>
<body onload="connect();">
<img id="img-id" src="" alt="">
Room id:
<p id="p-roomId">${requestScope.roomId}</p>
<button id="button-id" onclick="send();"></button>
<c:url value="/index.html" var="indexUrl"/>
<form name="index" method="POST" action="${indexUrl}">
    <input type="submit" value="Main page">
</form>
</body>
</html>
