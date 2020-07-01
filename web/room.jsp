<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Room</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript" src="websocket.js"></script>
</head>
<body>
<div class="content" id="content">
    <img id="img-id" src="" alt="">
    <div class="lamp-description">
        <br>
        Room id: <span id="p-roomId">${requestScope.roomId}</span>
        <br><br>
        <button id="button-id"></button>
        <br><br>
        <c:url value="/index.html" var="indexUrl"/>
        <form class="menu-form" name="index" method="POST" action="${indexUrl}">
            <input type="submit" value="Main page">
        </form>
    </div>
</div>
</body>
</html>
