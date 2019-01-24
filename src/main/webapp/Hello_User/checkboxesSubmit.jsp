<%--
  Created by IntelliJ IDEA.
  User: zwxu
  Date: 2018/12/4
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String[] fruits = request.getParameterValues("fruit");
%>
<html>
<head>
    <title>Hello User Application</title>
</head>
<body>
<h2>Your Selections</h2>
<%
    if (fruits == null) {
%>You did not select any fruits.<%
} else {
%>
<ul><%
    for (String fruit : fruits) {
        out.println("<li>" + fruit + "</li>");
    }
%></ul>
<%
    }
%>
</body>
</html>
