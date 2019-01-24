<%--
  Created by IntelliJ IDEA.
  User: zwxu
  Date: 2018/12/8
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello User Application</title>
</head>
<body>
settingOne: <%= application.getInitParameter("settingOne") %>,
settingTwo: <%= application.getInitParameter("settingTwo") %>
</body>
</html>
