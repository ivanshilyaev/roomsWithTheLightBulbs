<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create new room</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="content" id="content">
<c:url value="/createRoom.html" var="createRoomUrl"/>
<form name="room" method="POST" action="${createRoomUrl}">
    <label>
        Name: <br>
        <input type="text" name="name">
    </label> <br>
    <label>
        Country: <br>
        <select name="country" id="country">
            <c:forEach items="${requestScope.mapCountries}" var="country">
                <option value="${country.value}">${country.value}</option>
            </c:forEach>
        </select>
    </label> <br>
    <input type="submit" value="Create">
</form>
    ${requestScope.createRoomMessage} <br>
    <c:url value="/index.html" var="indexUrl"/>
    <form class="menu-form" name="index" method="POST" action="${indexUrl}">
        <input type="submit" value="Main page">
    </form>
</div>
</body>
</html>
