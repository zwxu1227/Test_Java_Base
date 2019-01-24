<%--
  Created by IntelliJ IDEA.
  User: zwxu
  Date: 2018/11/30
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map"%><%@page import="java.util.HashMap" %>
<%!
    private final int five = 0;

    protected String cowboy = "rodeo";

    //The assignment below is not declarative and is a syntax error if uncommented
//    cowboy = "test";

    public long addFive(long number)
    {
        return number + 5L;
    }

%>

<%= "Hello, World-zwxu" %><br />
<%= addFive(15L) %>
