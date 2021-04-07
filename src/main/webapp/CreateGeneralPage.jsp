<%@ page import="java.util.*" %>
<%@ page import="dto.Student" %>
<%@ page import="com.sun.org.apache.xpath.internal.operations.Number" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.02.2021
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Университет</title>
    <style>
        .colortext {background-color: #ffe; }
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>
<%
    String findFirstName=(String)request.getAttribute("findFirstName");
    String findSecondName=(String)request.getAttribute("findSecondName");
    String findBirthDay=(String)request.getAttribute("findBirthDay");
    String findEnterYear=(String)request.getAttribute("findEnterYear");
    int numberPage=(Integer)request.getAttribute("page");
    int pages=(Integer)request.getAttribute("pages");
    boolean messageLongInquiry=(Boolean)request.getAttribute("messageLongInquiry");
    int messageListEmpty=(Integer)request.getAttribute("messageListEmpty");
    Collection<Student> listTenStudents= (Collection<Student>) request.getAttribute("listTenStudents");
%>
<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
    <h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
    <div class="layer1">
        <a href="/university/option?ACTION=AddStudent">ADD STUDENT</a><br><br>
        <a href="/university/option?ACTION=FindStudent">FIND STUDENT</a><br><br>
        <a href="/university/option?ACTION=LIST OF SUBJECT">LIST OF SUBJECT</a><br><br>
    </div>
    <div class="layer2">
        <% if(messageListEmpty==1){%>
    <H1>Список студентов</H1>
        <%}else{
            if(messageListEmpty==0){%>
        <H1>Список пуст</H1>
        <%}}%>
       <% if(messageLongInquiry==true){%>
        <h1>Символов не должно быть больше 50</h1>
        <%}%>
    <table cellspacing="7" cellpadding="10" border="1">
        <tr><th>FirstName</th> <th>SecondName</th> <th> BirthDay </th> <th>EnterYear</th> <th>UPDATE</th> <th>DELETE</th> <th>MARK OF STUDENT</th></tr>
        <tr><form action="/university/option" method="POST">

            <%if((Boolean)request.getAttribute("colorFirstName")==true){%>
            <th><input type="text" class="colortext" name="findFirstName" value=<%=findFirstName%>></th>
            <%}else{%>
            <th><input type="text" name="findFirstName" value=<%=findFirstName%>></th>
            <%}%>

            <%if((Boolean)request.getAttribute("colorSecondName")==true){%>
            <th><input type="text" class="colortext" name="findSecondName"  value=<%=findSecondName%>></th>
            <%}else{%>
            <th><input type="text" name="findSecondName"  value=<%=findSecondName%>></th>
            <%}%>

            <%if((Boolean)request.getAttribute("colorBirthDay")==true){%>
            <th><input type="text" class="colortext" name="findBirthDay"  value=<%=findBirthDay%>></th>
            <%}else{%>
            <th><input type="text" name="findBirthDay"  value=<%=findBirthDay%>></th>
            <%}%>

            <%if((Boolean)request.getAttribute("colorEnterYear")==true){%>
            <th><input type="text" class="colortext" name="findEnterYear"  value=<%=findEnterYear%>></th>
            <%}else{%>
            <th><input type="text" name="findEnterYear"  value=<%=findEnterYear%>></th>
            <%}%>

            <th><input type="hidden" name="ACTION" value="ON GENERAL">
            <input type="submit"  value="SEARCH"></th>
            </form>
            <th></th>
            <th></th></tr>
        <%if (listTenStudents.size() != 0) {
            for (Student person : listTenStudents) {
        int id=person.getId();%>
                <tr><td><%=person.getFirstName() %> </td>
                        <td><%= person.getSecondName()%> </td>
                        <td><%=person.getBirthDay() %></td>
                        <td><%= person.getEnterYear() %></td>
                        <td><a href="/university/option?id=<%=id%>&ACTION=UpdateStudentFinish">UPDATE</a></td>
                        <td><a href="/university/option?id=<%=person.getId()%>&ACTION=AgreeToDeleteStudent">DELETE</a></td>
                        <td><a href="/university/option?idStudent=<%= person.getId()%>&ACTION=CreateListOfMarkForStudent">MARK</a></td></tr>)
           <%}%>
            </table>
    <% if ((numberPage - 1) > 0) {%>
            <a href="/university/option?page=<%=(numberPage - 1) %>&findFirstName=<%=findFirstName%>&findSecondName=<%=findSecondName%>&findEnterYear=<%=findEnterYear%>&findBirthDay=<%=findBirthDay%>&ACTION=ON GENERAL"><%= (numberPage - 1)%></a>
       <%}%>
               <a href="/university/option?page=<%=numberPage %>&findFirstName=<%=findFirstName%>&findSecondName=<%=findSecondName%>&findEnterYear=<%=findEnterYear%>&findBirthDay=<%=findBirthDay%>&ACTION=ON GENERAL"><%= numberPage %></a>
       <% if ((numberPage + 1)<= pages) {%>
            <a href="/university/option?page=<%=(numberPage + 1) %>&findFirstName=<%=findFirstName%>&findSecondName=<%=findSecondName%>&findEnterYear=<%=findEnterYear%>&findBirthDay=<%=findBirthDay%>&ACTION=ON GENERAL"><%= (numberPage + 1)%></a>
       <%}}%>
        </div>
                <div class="clear"></div>
</body>
</html>
