<%@ page import="dto.Student" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.02.2021
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Студенты</title>
    <style>
        .colortext {background-color: #ffe; }
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>

<%
    String firstName=(String)request.getAttribute("firstName");
    String secondName=(String)request.getAttribute("secondName");
    String birthDay=(String)request.getAttribute("birthDay");
    String enterYear=(String)request.getAttribute("enterYear");
    int id=(Integer)request.getAttribute("id");
    int countField=(Integer)request.getAttribute("countField");
%>

<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
<h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
<div class="layer1">
    <a href="/university/option?ACTION=AddStudent">ADD STUDENT</a><br><br>
    <a href="/university/option?ACTION=FindStudent">FIND STUDENT</a><br><br>
    <a href="/university/option?ACTION=LIST OF SUBJECT">LIST OF SUBJECT</a><br><br>
</div>
<div class="layer2">
   <% if(countField==4){%>
    <H1>Студент обновлен</H1>
    <%}else{%>
    <H1>Измените данные студента</H1>
    <%}%>
    <form action="/university/option" method="POST">

<%if((Boolean)request.getAttribute("colorFirstName")){%>
        <h1>Провертие правильность ввода</h1>
        FirstName <input type="text" class="colortext" name="firstName" value=<%=firstName%> ><br>
        <%}else{%>
        FirstName <input type="text" name="firstName" value=<%=firstName%> ><br>
        <%}%>

        <%if((Boolean)request.getAttribute("colorSecondName")){%>
        <h1>Провертие правильность ввода</h1>
        BirthDay <input type="text" class="colortext"  name="secondName" value=<%=secondName%>  ><br>
        <%}else{%>
        BirthDay <input type="text"  name="secondName" value=<%=secondName%>  ><br>
        <%}%>

        <%if((Boolean)request.getAttribute("colorBirthDay")){%>
        <h1>Провертие правильность ввода</h1>
        <input type="text" class="colortext" name="birthDay" value=<%=birthDay%>   ><br>
        <%}else{%>
        <input type="text" name="birthDay" value=<%=birthDay%>   ><br>
        <%}%>

        <%if((Boolean)request.getAttribute("colorEnterYear")){%>
        <h1>Провертие правильность ввода</h1>
        <input type="text" class="colortext" name="enterYear" value=<%=enterYear%> ><br><br>
        <%}else{%>
        <input type="text" name="enterYear" value=<%=enterYear%> ><br><br>
        <%}%>

    <input type="hidden" name="id" value=<%=id%>>
    <input type="hidden"  name="ACTION" value="UpdateStudentFinish">
    <input type="submit"   value="UPDATE"><br>
    </form>
    <a href="/university/option?ACTION=ON GENERAL">RETURN</a><br><br>
    </div>
<div class="clear"></div>
</body>
</html>
