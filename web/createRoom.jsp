<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create new room</title>
</head>
<body>
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
${requestScope.createRoomMessage}
</body>
</html>
