<%@ page import="dto.Subject" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 14.02.2021
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Предметы</title>
    <style>
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>
<%

   int messageAboutDelete=(Integer)request.getAttribute("messageAboutDelete");
%>
<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
<h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
<div class="layer1">
    <a href="/university/option?ACTION=AddSubject">ADD SUBJECT</a><br><br>
    <a href="/university/option?ACTION=FindSubject">FIND SUBJECT</a><br><br>
    <a href="/university/option?ACTION=ON GENERAL">LIST OF STUDENT</a><br><br>
    <a href="/university/option?ACTION=LIST OF SUBJECT">LIST OF SUBJECT</a><br><br>
</div>
<div class="layer2">
    <%
        if(messageAboutDelete==1){
            Subject subject=(Subject)request.getAttribute("subject");
    %>
    <H1>Вы действительно хотите удалить данный предмет</H1>
    <table cellspacing="5" cellpadding="7" border="1">
    <tr><th>NameSubject</th> <th>NameTeacher</th> <th>Kafedra</th></tr>
    <tr><td><%=subject.getName_subject()%></td>
         <td><%=subject.getName_teacher()%></td>
         <td><%=subject.getKafedra()%></td></tr></table>
    <form action="/university/option" method="POST" >
    <input type="hidden" name="id" value=<%=subject.getId()%> >
    <input type="hidden" name="ACTION" value="DeleteSubjectFinish">
    <input type="submit"  value="DELETE">
    </form>
    <%}else{%>
    <H1>Запись удалена</H1>
    <%}%>
    <a href="/university/option?ACTION=LIST OF SUBJECT">RETURN</a><br><br>
    </div>
<div class="clear"></div>
</body>
</html>
