<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Charge wallet</title>
</head>

<body>
Saldo: ${saldo} &euro;
<br>
<form action="ChargeWallet" method="post">
    Valor a Carregar:<input type="text" name="valor" pattern="[0-9]+"/><br/><br/>
    <input type="submit" value="Submit"/>
</form>

<a href="mainPage.jsp">Voltar</a>

</body>
</html>