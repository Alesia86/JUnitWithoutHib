<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 13.02.2021
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Предметы</title>
    <style>
        .colortext {background-color: #ffe; }
        .layer1 {padding: 5px; float: left; width: 200px;}
        .layer2 {padding: 5px; width: 300px; float: left;}
        .clear {clear: left; }
    </style>
</head>
<body>
<%
    String nameSubject=(String)request.getAttribute("nameSubject");
    String nameTeacher=(String)request.getAttribute("nameTeacher");
    String kafedra=(String)request.getAttribute("kafedra");
    int id=(Integer) request.getAttribute("id");
    int countField=(Integer)request.getAttribute("countField");
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
    <% if(countField==3){%>
    <H1>Предмет обновлен</H1>
    <%}else{%>
    <H1>Измените данные по предмету</H1>
    <%}%>
    <form action="/university/option" method="POST">
        <%if((Boolean)request.getAttribute("colorNameSubject")){%>
        <h1>Провертие правильность ввода</h1>
        NameSubject <input type="text" class="colortext"  name="nameSubject" value= <%=nameSubject%>  ><br>
        <%}else{%>
        NameSubject <input type="text" name="nameSubject" value= <%=nameSubject%>><br>
        <%}%>
        <%if((Boolean)request.getAttribute("colorNameTeacher")){%>
        <h1>Провертие правильность ввода</h1>
        NameTeacher <input type="text" class="colortext" name="nameTeacher" value= <%=nameTeacher%>  ><br>
        <%}else{%>
        NameTeacher <input type="text" name="nameTeacher" value= <%=nameTeacher%>  ><br>
        <%}%>
        <%if((Boolean)request.getAttribute("colorKafedra")){%>
        <h1>Провертие правильность ввода</h1>
        Kafedra <input type="text" class="colortext" name="kafedra" value= <%=kafedra%>  ><br>
        <%}else{%>
        Kafedra <input type="text"  name="kafedra" value= <%=kafedra%>  ><br>
        <%}%>

        <input type="hidden" name="id" value=<%=id%> >
        <input type="hidden"  name="ACTION" value="UpdateSubjectFinish">
        <input type="submit"   value="UPDATE"><br>
        </form>
    <a href="/university/option?ACTION=LIST OF SUBJECT">RETURN</a><br><br>
    </div>
    <div class="clear"></div>
</body>
</html>
