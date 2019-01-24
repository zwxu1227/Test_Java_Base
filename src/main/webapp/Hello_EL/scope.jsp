<%--
  Created by IntelliJ IDEA.
  User: zwxu
  Date: 2018/12/11
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%
    pageContext.setAttribute("a", "page");
    request.setAttribute("a", "request");
    session.setAttribute("a", "session");
    application.setAttribute("a", "application");

    request.setAttribute("b", "request");
    session.setAttribute("b", "session");
    application.setAttribute("b", "application");

    session.setAttribute("c", "session");
    application.setAttribute("c", "application");

    application.setAttribute("d", "application");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Scope Demonstration</title>
</head>
<body>
a = ${a}<br />
b = ${b}<br />
c = ${c}<br />
d = ${d}<br />
</body>
</html>
