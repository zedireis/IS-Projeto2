<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 10/11/2021
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<h1>Bem-vindo</h1>

<a href="userEditInfo.jsp">Editar Informação Pessoal</a><br>
<a href="deleteAccount.jsp">Apagar Conta</a><br><br>
<a href="ChargeWallet">Carregar Carteira</a><br>
<a href="AvailableTrips.jsp">Viagens disponiveis</a><br>
<a href="purchaseticket">Comprar bilhete</a><br>
<a href="refundticket">Reembolsar bilhete</a><br>
<a href="tripList">As minhas viagens</a><br>

<c:if test="${sessionScope.manager}">
    <br><h2>Manager Options</h2>
    <a href="criarTrip.jsp">Criar uma viagem</a><br>
    <a href="deletetrip">Eliminar uma viagem</a><br>
    <a href="topfive">Top 5 passageiros</a><br>
    <a href="SearchSorted.jsp">Pesquisa ordenada no intervalo e lista de passageiros</a><br>
    <a href="searchByDate.jsp">Pesquisa de viagem por data e lista de passageiros</a><br>
</c:if>

<br>
<form action="logout" method="post">
    <input type="submit" value="Logout" >
</form>

</body>
</html>
