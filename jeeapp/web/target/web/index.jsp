<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>

<body>

<c:if test="${sessionScope.auth != null}">
    <%
        response.sendRedirect("/web/secured/mainPage.jsp");
    %>
</c:if>

<form action="login" method="post">
    Email:<input required="required" type="text" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"/><br/><br/>
    Password:<input required="required" type="password" name="password"/><br/><br/>
    <input type="submit" value="login"/>
</form>

<a href="Registo.jsp">Registo</a>

</body>
</html>