<%@ page import="dto.Mark" %>
<%@ page import="dto.Student" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16.02.2021
  Time: 17:50
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
    int idStudent=(Integer)request.getAttribute("idStudent");
    int idMark=(Integer)request.getAttribute("idMark");
    Student student=(Student)request.getAttribute("student");
    Mark mark=(Mark)request.getAttribute("mark");
    int messageErrorMark=(Integer)request.getAttribute("messageErrorMark");
    boolean messageEnter=(Boolean) request.getAttribute("messageEnter");
    String markStudent=(String)request.getAttribute("markStudent");
    String color="";
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

        <%if(!messageEnter){%>
    <H1>Введите оценку для студента</H1>
    <form action="/university/option" method="POST">
        FirstName <input type="text"  value=<%=student.getFirstName()%> readonly >
        SecondName<input type="text"  value=<%=student.getSecondName()%> readonly >
        Subject<input type="text"  value=<%=mark.getNameOfSubject()%> readonly >

            <%if(messageErrorMark==1){%>
        <h1>Заполните поле оценки</h1>
        Mark<input type="text" class="colortext" name="mark" value=<%=markStudent%> >
        <%}else{
            if(messageErrorMark==2){%>
                <h1>Неверный формат оценки</h1>
        Mark<input type="text" class="colortext" name="mark" value=<%=markStudent%> >
            <%}else{
                if(messageErrorMark==0){%>
        Mark<input type="text" name="mark" value=<%=mark.getMark()%> >
        <%}}}%>
        <input type="hidden" name="idStudent" value=<%=idStudent%> >
        <input type="hidden" name="idMark" value=<%=idMark%> >
        <input type="hidden"  name="ACTION" value="UpdateMarkFinish">
        <input type="submit"   value="UPDATE MARK">
    </form>
                <%}else{%>
            <H1>Оценка обновлена</H1>
                <%}%>
    <a href="/university/option?idStudent=<%=idStudent%>&ACTION=CreateListOfMarkForStudent">RETURN</a><br><br>
</body>
</html>
