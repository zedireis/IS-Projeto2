<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Available Trips</title>
</head>

<body>
<h3>Pesquisar Viagens Disponíveis</h3><br>
<form action="tripsavailable" method="get">
    Data de inicio: <input required="required" name="dateInicio" type="date"> <input required="required" name="horaPartida" type="time"><br>
    Data de fim: <input required="required" name="dateFim" type="date"><input required="required" name="horaChegada" type="time"><br>
    <br><input type="submit" value="Submit"/><br>
</form>

<c:forEach var="item" items="${myListOfTrips}">
    <div>${item}</div>
</c:forEach>

<a href="mainPage.jsp">Voltar</a>

</body>
</html>