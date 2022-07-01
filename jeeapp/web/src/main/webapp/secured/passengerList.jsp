<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista de passageiros</title>
</head>

<body>

<h2>Lista de passageiros</h2>
<c:forEach var="item" items="${myListOfTrips}">
    <div>${item}</div>
</c:forEach>

<br>
<a href="mainPage.jsp">Voltar</a>

</body>
</html>