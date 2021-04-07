<%@ page import="dto.Subject" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.02.2021
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Университет</title>
    <style>
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>
<%
    boolean messageListEmpty=(Boolean) request.getAttribute("messageListEmpty");
    Collection<Subject> list= (Collection<Subject>) request.getAttribute("list");
%>
<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
<h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
<div class="layer1">
    <a href="/university/option?ACTION=AddSubject">ADD SUBJECT</a><br><br>
    <a href="/university/option?ACTION=FindSubject">FIND SUBJECT</a><br><br>
    <a href="/university/option?ACTION=ON GENERAL">LIST OF STUDENT</a><br><br>
</div>
<div class="layer2">
    <% if(messageListEmpty==true){%>
<H1>Список предметов</H1>
    <%}else{%>
    <H1>Список пуст</H1>
    <%}%>
<table cellspacing="7" cellpadding="10" border="1">
    <tr><th>NameSubject</th> <th>NameTeacher</th> <th>Kafedra</th> <th>UPDATE</th> <th>DELETE</th> </tr>
   <%for (Subject subject : list) {%>
    <tr><td><%=subject.getName_subject()%></td>
        <td><%=subject.getName_teacher()%></td>
        <td><%=subject.getKafedra()%></td>
        <td><a href="/university/option?id=<%=subject.getId()%>&ACTION=UpdateSubjectFinish">UPDATE</a></td>
        <td><a href="/university/option?id=<%=subject.getId()%>&ACTION=AgreeToDeleteSubject">DELETE</a></td><tr>
        <%}%>
</table>
</div>
<div class="clear"></div>
</body>
</html>
