<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 22.02.2021
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<%String message =pageContext.getException().getMessage();
String exception = pageContext.getException().getClass().toString();%>
<h2>Exception</h2>
<p>Type: <%=exception%></p>
<p>Message: <%=message%>/p>
</body>
</html>
