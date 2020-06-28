<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<c:url value="/room.jsp" var="roomUrl"/>
<form name="goToRoom" method="POST" action="${roomUrl}">
    <input type="submit" value="Room">
</form>
</body>
</html>
