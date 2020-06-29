<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Error page</title>
</head>
<body>
<%@page isErrorPage="true" %>

<c:choose>
    <c:when test="${not empty requestScope.error}">
        ${requestScope.error}
    </c:when>
    <c:when test="${not empty pageContext.errorData.requestURI}">
        Request from ${pageContext.errorData.requestURI} is failed <br/>
        Servlet name or type: ${pageContext.errorData.servletName} <br/>
        Status code: ${pageContext.errorData.statusCode} <br/>
        Exception: ${pageContext.errorData.throwable}
    </c:when>
    <c:otherwise>Unknown error</c:otherwise>
</c:choose>

</body>
</html>
