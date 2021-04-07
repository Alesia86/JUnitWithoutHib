<%@ page import="dto.Subject" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15.02.2021
  Time: 21:33
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
    String findSubject=(String)request.getAttribute("findSubject");
    int idStudent=(Integer)request.getAttribute("idStudent");
    boolean colorNameSubject=(Boolean) request.getAttribute("colorNameSubject");
    Collection<Subject> listSubject=(Collection<Subject>)request.getAttribute("listSubject");
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
        <% if (listSubject.size() == 0) {%>
    <H1>Список предметов пуст</H1>
        <%}else{%>
    <H1>Список предметов</H1>
            <%
             if(colorNameSubject){
                 color="class=\"colortext\" ";
                %>
    <h1>Название предмета не должно содержать >50 символов</h1>
            <%}%>
            <table cellspacing="5" cellpadding="7" border="1">
        <tr><th>Subject</th> <th>NameTeacher</th> <th>Kafedra</th> <th>ADD MARK</th></tr>
        <form action="/university/option" method="POST">
            <tr>
                <th><input type="text" <%=color%> name="findSubject"  value=<%=findSubject%>> </th>
                <th>
                    <input type="hidden" name="idStudent" value= <%=idStudent%> >
                    <input type="hidden" name="ACTION" value="PrepareToAddMark">
                    <input type="submit"  value="SEARCH"></th>
                <th></th>
                <th></th></tr>
                <%for (Subject subject : listSubject) {
           %>
            <tr><td><%=subject.getName_subject()%></td>
            <td><%=subject.getName_teacher()%></td>
                <td><%=subject.getKafedra()%></td>
            <td><a href="/university/option?idStudent=<%=idStudent%>&idSubject=<%=subject.getId()%>&ACTION=AddMarkFinish">ADD MARK</a></td></tr>
                <%}}%>

    </table>
            <a href="/university/option?idStudent=<%=idStudent%>&ACTION=CreateListOfMarkForStudent">RETURN</a><br><br>
</body>
</html>
