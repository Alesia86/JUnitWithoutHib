<%@ page import="dto.Student" %>
<%@ page import="dto.Mark" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16.02.2021
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Оценки</title>
    <style>
        .colortext {background-color: #ffe; }
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>
<%

    int messageAboutDelete=(Integer)request.getAttribute("messageAboutDelete");
    int idStudent=(Integer)request.getAttribute("idStudent");
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
            Student  student=(Student)request.getAttribute("student");
            Mark mark=(Mark)request.getAttribute("mark");
    %>
    <H1>Вы действительно хотите удалить оценку данного студента</H1>
            <table cellspacing="5" cellpadding="7" border="1">
            <tr><th>FirstName</th><th>SecondName</th> <th>Subject</th> <th>Mark</th></tr>
            <tr><td><%=student.getFirstName()%></td>
                 <td><%=student.getSecondName()%></td>
                <td><%=mark.getNameOfSubject()%></td>
                <td><%=mark.getMark()%></td></tr></table>
            <form action="/university/option" method="POST" >
            <input type="hidden" name="idMark" value=<%=mark.getId()%> >
            <input type="hidden" name="idStudent" value=<%=student.getId()%> >
            <input type="hidden" name="ACTION" value="DeleteMarkFinish">
            <input type="submit"  value="DELETE">
            </form>
                <%}else{%>
            <H1>Запись удалена</H1>
                <%}%>
    <a href=\"/university/option?idStudent=<%=idStudent%>&ACTION=CreateListOfMarkForStudent">RETURN</a><br><br>
    </div>
<div class="clear"></div>
</body>
</html>
