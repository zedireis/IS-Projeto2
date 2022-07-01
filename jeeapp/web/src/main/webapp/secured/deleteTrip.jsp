<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Delete Trip</title>
</head>

<body>

<h1>Eliminar Viagem</h1><br>
<c:choose>
    <c:when test="${empty myListOfTrips}">Sem trips</c:when>
    <c:otherwise>
        <form action="deletetrip" method="post">
            ID da viagem:<input type="text" name="tripid"/><br/><br/>
            <input type="submit" value="Delete"/><br><br>
        </form>

        <c:forEach var="item" items="${myListOfTrips}">
            <div>${item}</div>
        </c:forEach>
    </c:otherwise>
</c:choose>

<br>
<br><a href="mainPage.jsp">Voltar</a>


</body>
</html>