<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of rooms</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="content" id="content">
<c:if test="${sessionScope.rooms.size() != 0}">
    <table>
    <c:forEach items="${sessionScope.rooms}" var="room">
        <tr>
            <td>
                <c:url value="/room.html" var="roomUrl"/>
                <form class="menu-form" name="room" method="POST" action="${roomUrl}">
                    <input type="hidden" name="roomId" value="${room.id}">
                    <input type="submit" value="<c:out value="${room.name}: ${room.country}"/>">
                </form>
            </td>
        </tr>
        </table>
    </c:forEach>
    <br>
    <c:url value="/index.html" var="indexUrl"/>
    <form class="menu-form" name="index" method="POST" action="${indexUrl}">
        <input type="submit" value="Main page">
    </form>
</c:if>
<c:if test="${sessionScope.rooms.size() == 0}">
    List is empty!
</c:if>
</div>
</body>
</html>
