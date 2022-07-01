<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SearchByDate</title>
</head>

<body>
<h2>Pesquisar viagens na data</h2>
<form action="searchbydate" method="get">
    Data de partida: <input required="required" name="dateInicio" type="date">
    <br><br><input type="submit" value="Pesquisar"/><br>
</form>

<br>
<h4>Viagens disponíveis</h4>
<br>
<form action="searchbydate" method="post">
    <select name="viagem">
        <c:forEach items="${listDestinos}" var="viagem">
            <option value="${viagem.key}">${viagem.value}</option>
        </c:forEach>
    </select>
    <br>
    <input type="submit" value="Ver Lista de Passageiros"/><br>
</form>

<br><br>
<a href="mainPage.jsp">Voltar</a>

</body>
</html>