<%@ page import="dto.Student" %>
<%@ page import="dto.Subject" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16.02.2021
  Time: 14:33
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
    boolean messageEnter=(Boolean) request.getAttribute("messageEnter");
    String markStudent=(String)request.getAttribute("markStudent");

    int idStudent=(Integer)request.getAttribute("idStudent");
    int idSubject=(Integer)request.getAttribute("idSubject");
    Student student=(Student)request.getAttribute("student");
    Subject subject=(Subject)request.getAttribute("subject");
    boolean colorMarkStudent=(Boolean) request.getAttribute("colorMarkStudent");
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
        Subject<input type="text"  value=<%=subject.getName_subject()%> readonly >
        <%if(colorMarkStudent){%>
        <h1>Проверьте правильность введенной оценки</h1>
        Mark<input type="text" class="colortext" name="mark" value=<%=markStudent%> >
        <%}else{%>
        Mark<input type="text" name="mark" value=<%=markStudent%> >
        <%}%>
        <input type="hidden" name="idStudent" value=<%=idStudent%> >
        <input type="hidden" name="idSubject" value=<%=idSubject%> >
        <input type="hidden"  name="ACTION" value="AddMarkFinish">
        <input type="submit"   value="ADD MARK">
        </form>
    <%}else{%>
    <H1>Оценка для студента добавлена</H1>
    <%}%>
        <a href="/university/option?idStudent=<%=idStudent%>&ACTION=PrepareToAddMark">RETURN</a><br><br>
    </div>
    <div class="clear"></div>
</body>
</html>
