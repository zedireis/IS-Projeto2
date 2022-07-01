<%--
  Created by IntelliJ IDEA.
  User: bruno
  Date: 12/11/2021
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Trip</title>
</head>
<body>
<h2>Criar Trips</h2>

<form action="criartrip" method="post">
<%--    Partida:<input required="required" type="text" name="partida"/><br/><br/>--%>
    Partida: <select name="partida">
        <option value="Ancora">Ancora</option>
        <option value="Braga">Braga</option>
        <option value="Coimbra">Coimbra</option>
        <option value="Faro">Faro</option>
        <option value="Lisboa">Lisboa</option>
        <option value="Porto">Porto</option>
    </select><br><br>
<%--    Chegada:<input required="required" type="text" name="chegada"/><br/><br/>--%>
    Destino: <select name="chegada">
        <option value="Ancora">Ancora</option>
        <option value="Braga">Braga</option>
        <option value="Coimbra">Coimbra</option>
        <option value="Faro">Faro</option>
        <option value="Lisboa">Lisboa</option>
        <option value="Porto">Porto</option>
    </select><br><br>
    Capacidade:<input required="required" type="text" name="capacidade"/><br/><br/>
    Preço:<input required="required" type="text" name="preco"/><br/><br/>
    Data de Partida: <input required="required" name="dateInicio" type="date">  <input required="required" name="horaPartida" type="time"><br>
    Data de Chegada: <input  required="required" name="dateFim" type="date"> <input required="required" name="horaChegada" type="time"><br>
    <input type="submit" value="Submit"/>
</form>


<br><a href="mainPage.jsp">Voltar</a>

</body>
</html>
