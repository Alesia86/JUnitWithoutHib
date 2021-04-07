<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 22.02.2021
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Станица ошибки</title>
    <style>
        .colortext {background-color: #ffe; }
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>
<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
<h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
<div class="layer1">
    <a href="/university/option?ACTION=AddStudent">ADD STUDENT</a><br><br>
    <a href="/university/option?ACTION=FindStudent">FIND STUDENT</a><br><br>
    <a href="/university/option?ACTION=LIST OF SUBJECT">LIST OF SUBJECT</a><br><br>
</div>
<div class="layer2">
    <%switch ((String)request.getAttribute("errorMessage")){
        case "daoException":%>
            <H1>Ошибка при работе с базой данных</H1>
    <% break;
        case "closeDaoException":%>
    <H1>Ошибка при закрытии DAO</H1>
    <% break;
        case "closeDaoException":%>

    %>

</div>
<div class="clear"></div>

</body>
</html>
