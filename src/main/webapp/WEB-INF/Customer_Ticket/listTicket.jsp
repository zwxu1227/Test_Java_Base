<%--
  Created by IntelliJ IDEA.
  User: zwxu
  Date: 2018/12/8
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page session="false" import="java.util.Map" %>
<%
    @SuppressWarnings("unchecked")
    Map <Integer, Ticket> ticketDatabase = (Map <Integer, Ticket>) request.getAttribute("ticketDatabase");

%>
<html>
<head>
    <title>Coutomer Ticket</title>
</head>
<body>
<h2>Tickets</h2>
<a href="<c:url value="/tickets">
<c:param name="action" value="create"/>
</c:url>">Create Ticket</a><br/><br/>
<%
    if (ticketDatabase.size() == 0) {
%><i>There are no tickets in the system</i><%
} else {
    for (int id : ticketDatabase.keySet()) {
        String idString = Integer.toString(id);
        Ticket ticket = ticketDatabase.get(id);
%>
Ticket # <%=idString%>:<a href="<c:url value="/tickets">
    <c:param name="action" value="view"/>
    <c:param name="ticketId" value="<%=idString%>"/>
</c:url>" ><%=ticket.getSubject()%></a>
customer:<%= ticket.getCustomerName() %><br />
<%
        }
    }
%>
</body>
</html>
