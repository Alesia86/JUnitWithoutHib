<%@ page import="dto.Student" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 14.02.2021
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Студенты</title>
    <style>
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>

<%
    int messageAboutDelete =(Integer) request.getAttribute("messageAboutDelete") ;
%>
<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
<h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
<div class="layer1">
    <a href="/university/option?ACTION=AddStudent">ADD STUDENT</a><br><br>
    <a href="/university/option?ACTION=FindStudent">FIND STUDENT</a><br><br>
    <a href="/university/option?ACTION=ON GENERAL">LIST OF STUDENT</a><br><br>
    <a href="/university/option?ACTION=LIST OF SUBJECT">LIST OF SUBJECT</a><br><br>
</div>
<div class="layer2">

    <%
        if(messageAboutDelete==1){
            Student student=(Student)request.getAttribute("student");
    %>
    <H1>Вы действительно хотите удалить данного студента</H1>
    <table cellspacing="5" cellpadding="7" border="1">
        <tr><th>FirstName</th> <th>SecondName</th> <th>BirthDay</th> <th>EnterYear</th></tr>
        <tr><td><%=student.getFirstName()%></td>
            <td><%=student.getSecondName()%></td>
            <td><%=student.getBirthDay()%></td>
            <td><%=student.getEnterYear()%></td></tr></table>
    <form action="/university/option" method="POST" >
        <input type="hidden" name="id" value=<%=student.getId()%>>
        <input type="hidden" name="ACTION" value="DeleteStudentFinish">
        <input type="submit"  value="DELETE">
        </form>
    <%}else{%>
    <H1>Запись удалена</H1>
    <%}%>
    <a href="/university/option?ACTION=ON GENERAL">RETURN</a>
    </div>
<div class="clear"></div>
</body>
</html>
