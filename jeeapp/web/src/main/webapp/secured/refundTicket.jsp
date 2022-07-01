<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Refund Ticket</title>
</head>

<body>

<h2>Bilhetes Futuros</h2>


    <c:choose>
        <c:when test="${empty myAllUnstartedTrips}">Sem trips</c:when>
        <c:otherwise>
            <c:forEach var="item" items="${myAllUnstartedTrips}">
                <div>${item}</div>
            </c:forEach>

            <br>
            <form action="refundticket" method="post">
                <strong>ID do bilhete a reembolsar: </strong><input required="required" type="text" name="ticketid"/><br/><br/>
                <input type="submit" value="Devolver"/><br>
            </form>
        </c:otherwise>
    </c:choose>



<br>
<a href="mainPage.jsp">Voltar</a>

</body>
</html>