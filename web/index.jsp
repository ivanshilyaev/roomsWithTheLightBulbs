<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="content" id="content">
    <h2>Rooms with the light bulbs</h2>
    <c:url value="/createRoom.html" var="createRoomUrl"/>
    <form class="menu-form" name="createRoom" method="POST" action="${createRoomUrl}">
        <input type="submit" value="Create new room">
    </form>
    <br>
    <c:url value="/listOfRooms.html" var="listOfRoomsUrl"/>
    <form class="menu-form" name="listOfRooms" method="POST" action="${listOfRoomsUrl}">
        <input type="submit" value="List of all rooms">
    </form>
</div>
</body>
</html>
