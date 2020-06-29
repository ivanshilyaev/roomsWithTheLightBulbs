<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of rooms</title>
</head>
<body>
<c:if test="${sessionScope.rooms.size() != 0}">
    <table>
    <c:forEach items="${sessionScope.rooms}" var="room">
        <tr>
            <td>
                <c:url value="/room.html" var="roomUrl"/>
                <form name="room" method="POST" action="${roomUrl}">
                    <input type="hidden" name="roomId" value="${room.id}">
                    <input type="submit" value="<c:out value="${room.name}: ${room.country}"/>">
                </form>
            </td>
        </tr>
        </table>
    </c:forEach>
</c:if>
<c:if test="${sessionScope.rooms.size() == 0}">
    List is empty!
</c:if>
</body>
</html>
