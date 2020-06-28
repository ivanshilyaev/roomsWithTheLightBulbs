<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<c:url value="/createRoom.html" var="createRoomUrl"/>
<form name="createRoom" method="POST" action="${createRoomUrl}">
    <input type="submit" value="Create new room">
</form>
<br>
<c:url value="/listOfRooms.html" var="listOfRoomsUrl"/>
<form name="listOfRooms" method="POST" action="${listOfRoomsUrl}">
    <input type="submit" value="List of all rooms">
</form>
</body>
</html>
