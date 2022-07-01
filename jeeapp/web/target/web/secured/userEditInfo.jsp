<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit info</title>
</head>

<body>
<form action="editinfo" method="post">
    Nome:<input type="text" name="name"/><br/><br/>
    Email:<input type="text" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"/><br/><br/>
    Password:<input type="text" name="password" pattern="([0-9a-zA-Z!@#$%^&*_=+-]+)"/><br/><br/>
    <input type="submit" value="Submit"/>
</form>

<a href="mainPage.jsp">Voltar</a>

</body>
</html>