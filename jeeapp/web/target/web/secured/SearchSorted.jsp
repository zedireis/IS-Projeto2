<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>SearchSorted</title>
</head>

<body>

<form action="searchsorted" method="get">
  Data de inicio: <input required="required" name="dateInicio" type="date"> <input required="required" name="horaPartida" type="time"><br>
  Data de fim: <input required="required" name="dateFim" type="date"><input required="required" name="horaChegada" type="time"><br>
  <br><input type="submit" value="Submit"/><br>
</form>

<c:forEach var="item" items="${myListOfTripsSorted}">
  <div>${item}</div>
</c:forEach>

<br>
<h3>Ver lista de passageiros</h3>
<form action="searchsorted" method="post">
  <select name="viagem">
    <c:forEach items="${listDestinos}" var="viagem">
      <option value="${viagem.key}">${viagem.value}</option>
    </c:forEach>
  </select>
  <br>
  <input type="submit" value="Ver Lista de Passageiros"/><br>
</form>

<a href="mainPage.jsp">Voltar</a>

</body>
</html>