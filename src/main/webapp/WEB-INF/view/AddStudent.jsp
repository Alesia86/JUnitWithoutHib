<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 17.02.2021
  Time: 18:45
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
    String colorFirstName=(String)session.getAttribute("colorFirstName");
    String colorSecondName =(String)session.getAttribute("colorSecondName");
    String colorEnterYear=(String)session.getAttribute("colorEnterYear");
    String colorBirthDay= (String)session.getAttribute("colorBirthDay");
    String firstName = (String)session.getAttribute("firstName");
    String secondName=(String)session.getAttribute("secondName");
    String enterYear= (String)session.getAttribute("enterYear");
    String birthDay=(String)session.getAttribute("birthDay");
    String errorMessageFirstName= (String)session.getAttribute("errorMessageFirstName");
    String errorMessageSecondName= (String)session.getAttribute("errorMessageSecondName");
    String errorMessageEnterYear= (String)session.getAttribute("errorMessageEnterYear");
    String errorMessageBirthDay=(String)session.getAttribute("errorMessageBirthDay");
    String enterMessage=(String)session.getAttribute("enterMessage");
    int count=(Integer) session.getAttribute("count");
%>
<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
<h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
<div class="layer1">
    <a href="/university/option?ACTION=FindStudent">FIND STUDENT</a><br><br>
    <a href="/university/option?ACTION=LIST OF SUBJECT">LIST OF SUBJECT</a><br><br>
    <a href="/university/option?ACTION=ON GENERAL">LIST OF STUDENT</a><br><br>
</div>
<div class="layer2">
    <H1><%=enterMessage%></H1>
    <form action="/university/option" method="POST">
        <h1><%=errorMessageFirstName%></h1>
   FirstName <input type="text" <%=colorFirstName%> name="firstName" value=<%=firstName%> ><br>
        <h1><%=errorMessageSecondName%></h1>
    SecondName <input type="text" <%=colorSecondName%>   name="secondName" value= <%=secondName%>  ><br>
        <h1><%=errorMessageBirthDay%></h1>
    BirthDay <input type="text" <%=colorBirthDay%>  name="birthDay" value=<%=birthDay%>  ><br>
        <h1><%=errorMessageEnterYear%></h1>
    EnterYear <input type="text" <%=colorEnterYear%>  name="enterYear" value= <%=enterYear%>  ><br>
       <% if(count==0){%>
    <br><br><input type="hidden" name="ACTION" value="AddStudent">
    <input type="submit"  value="ADD">
    </form>
    <%}%>
    <td><a href="/university/option?ACTION=ON GENERAL">RETURN</a></td><br><br>
    </div>
<div class="clear"></div>
</body>
</html>
