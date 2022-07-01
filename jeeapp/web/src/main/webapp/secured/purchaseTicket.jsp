<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Purchase Ticket</title>
</head>

<body>
<h2>Comprar bilhete</h2>
<form action="purchaseticket" method="get">
    Data de partida: <input name="dateInicio" type="date"><br><br>
    Hora da partida: <input name="horaPartida" type="time"><br><br>
    Destino:
    <select name="destino">
        <option value=""></option>
        <c:forEach items="${listDestinos}" var="destino">
            <option value="${destino}">${destino}</option>
        </c:forEach>
    </select>
    <br><br><input type="submit" value="Pesquisar"/><br>
</form>

<br>
<h4>Viagens disponíveis</h4>
<c:forEach var="item" items="${myListOfTrips}">
    <div>${item}</div>
</c:forEach>
<br>
<h3>Finalizar compra</h3>
<br>
<form action="purchaseticket" method="post">
    ID da viagem:<input type="text" name="tripid"/><br/><br/>
    <input type="submit" value="Comprar"/><br>
</form>

<br><br>
<a href="mainPage.jsp">Voltar</a>

</body>
</html>