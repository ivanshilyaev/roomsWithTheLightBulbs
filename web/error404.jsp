<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="css/404.css">
    <title>404</title>
</head>
<body class="bg-purple">

<%@page isErrorPage="true" %>

<div class="stars">
    <div class="custom-navbar">
        <div class="brand-logo">
        </div>
        <div class="navbar-links">
        </div>
    </div>
    <div class="central-body">
        <img class="image-404" src="http://salehriaz.com/404Page/img/404.svg" width="300px">
        <c:url value="/index.html" var="indexUrl"/>
        <form class="btn-go-home" name="findAllStudentsFromTheGroup" method="POST"
              action="${indexUrl}">
            <input type="submit" value="GO BACK">
        </form>
    </div>
    <div class="objects">
        <img class="object_rocket" src="http://salehriaz.com/404Page/img/rocket.svg" width="40px">
        <div class="earth-moon">
            <img class="object_earth" src="http://salehriaz.com/404Page/img/earth.svg" width="100px">
            <img class="object_moon" src="http://salehriaz.com/404Page/img/moon.svg" width="80px">
        </div>
        <div class="box_astronaut">
            <img class="object_astronaut" src="http://salehriaz.com/404Page/img/astronaut.svg" width="140px">
        </div>
    </div>
    <div class="glowing_stars">
        <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
        <div class="star"></div>
    </div>

</div>

</body>
</html>
