<%@ page import="java.util.Collection" %>
<%@ page import="dto.Student" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 17.02.2021
  Time: 14:11
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
    String secondName=(String)session.getAttribute("secondName");
    String color="";
    if (secondName==null){
        secondName="";
    }
    int enterMessage=(Integer)request.getAttribute("enterMessage");
    Collection<Student> list=(Collection<Student>)request.getAttribute("list");
    int count=(Integer)request.getAttribute("count");
%>


<h1 align="center" style="background-color: MediumSeaGreen; ">                       БЕЛАРУССКИЙ НАЦИОНАЛЬНЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ</h1>
<h2 align="center" style="background-color: LightGray; ">                                                     ПРИБОРОСТРОИТЕЛЬНЫЙ ФАКУЛЬТЕТ</h2>
<div class="layer1">
    <a href="/university/option?ACTION=AddStudent">ADD STUDENT</a><br><br>
    <a href="/university/option?ACTION=ON GENERAL">LIST OF STUDENT</a><br><br>
    <a href="/university/option?ACTION=LIST OF SUBJECT">LIST OF SUBJECT</a><br><br>
</div>
<div class="layer2">
    <%switch (enterMessage){
        case 0:%>
       <H1>Введите фамилию студента</H1>
   <%break;
       case 1:%>
    <H1>Список найденных студентов</H1>
    <%break;
        case 2:%>
    <H1>Такой студент не найден</H1>
    <%break;
    }%>
    <%if((Boolean) request.getAttribute("colorSecondName")){
        color="class=\"colortext\"";
    }%>
    <br><form action="/university/option" method="POST">
    SecondName <input type="text" <%=color%> name="secondName" value=<%=secondName%> >
    <input type="hidden" name="ACTION" value="FindStudent">
    <input type="submit" value="FIND">
    </form>

    <%if(count==0 && list.size()!=0){%>
    <table cellspacing="5" cellpadding="7" border="1">
    <tr><th>FirstName</th> <th>SecondName</th> <th>BirthDay</th> <th>EnterYear</th> <th>UPDATE</th> <th>DELETE</th> <th>MARK OF STUDENT</th></tr>
    <%for (Student student : list) {%>
    <tr><td><%=student.getFirstName()%></td>
        <td><%=student.getSecondName()%></td>
        <td><%=student.getBirthDay()%></td>
        <td><%=student.getEnterYear()%></td>
        <td><a href="/university/option?id=<%=student.getId()%>&ACTION=UpdateSubjectFinish">UPDATE</a></td>
        <td><a href="/university/option?id=<%=student.getId() %>&ACTION=AgreeToDeleteStudent">DELETE</a></td>
        <td><a href="/university/option?idStudent=<%=student.getId() %>&ACTION=CreateListOfMarkForStudent">MARK</a></td></tr>
        <%}
    }%>
    <a href="/university/option?ACTION=ON GENERAL">RETURN</a><br><br>
    </div>
<div class="clear"></div>
</body>
</html>
