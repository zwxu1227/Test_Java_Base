<%--
  Created by IntelliJ IDEA.
  User: zwxu
  Date: 2018/12/8
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>

<%--@elvariable id="ticketDatabase" type="java.util.Map<Integer, Ticket>"--%>

<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<h2>Tickets</h2>
<a href="<c:url value="/tickets">
<c:param name="action" value="create"/>
</c:url>">Create Ticket</a><br/><br/>
<c:choose>
    <c:when test="${fn.length(ticketDatabase)==0}">
        <i>There are no tickets in the system</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${ticketDatabase}" var="entry">
            Ticket${entry.key};<a href="<c:url value="/tickets">
            <c:param name="action" value="view"/>
            <c:param name="ticketId" value="${entry.key}"/>
        </c:url>"><c:out value="${entry.value.customerName}"/></a>

        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
