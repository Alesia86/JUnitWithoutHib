<%@ page import="java.util.Collection" %>
<%@ page import="dto.Mark" %>
<%@ page import="dto.Student" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15.02.2021
  Time: 18:16
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
    Collection<Mark> listMark=(Collection<Mark>)request.getAttribute("listMark");
    Student student=(Student)request.getAttribute("student");
    boolean colorNameSubject=(Boolean) request.getAttribute("colorNameSubject");
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
   <% if (listMark.size() == 0) {%>
    <H1>Данный студент без оценок</H1>
       <%}else{%>
           <H1>Список оценок</H1>
    <%
        if(colorNameSubject){
            color="class=\"colortext\" ";
    %>
    <h1>Название предмета не должно содержать >50 символов</h1>
    <%}%>
    <table cellspacing="5" cellpadding="7" border="1">
        <tr><th>FirstName</th> <th>SecondName</th> <th>Subject</th> <th>Mark</th> <th>UPDATE MARK</th> <th>DELETE MARK</th> </tr>
        <form action="/university/option" method="POST">
            <tr><th></th>
                <th></th>
                <th><input type="text" <%=color%> name="findSubject"  value=<%=findSubject%>> </th>
                <th>
                    <input type="hidden" name="idStudent" value= <%=idStudent%> >
                    <input type="hidden" name="ACTION" value="CreateListOfMarkForStudent">
                    <input type="submit"  value="SEARCH"></th>
                <th></th>
                <th></th></tr>
          <% for (Mark mark : listMark) {
           %>
               <tr><td><%=student.getFirstName()%></td>
                       <td><%=student.getSecondName()%></td>
                        <td><%= mark.getNameOfSubject()%></td>
                        <td><%=mark.getMark()%></td>+
                       <td><a href="/university/option?idStudent=<%=idStudent%>&idMark=<%=mark.getId()%>&ACTION=UpdateMarkFinish">UPDATE MARK</a></td>
                       <td><a href="/university/option?idStudent=<%=idStudent%>&idMark=<%=mark.getId()%>&ACTION=AgreeToDeleteMark">DELETE MARK</a></td></tr>
        <%}%>
            </table>
        <a href="/university/option?idStudent=<%=idStudent%>&ACTION=PrepareToAddMark">ADD MARK</a><br><br>
        <a href="/university/option?ACTION=ON GENERAL">RETURN</a><br><br><br><br>
    <%}%>
        </div>
<div class="clear"></div>
    </form>
</body>
</html>
