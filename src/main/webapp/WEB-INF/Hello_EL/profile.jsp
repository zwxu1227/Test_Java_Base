<%--
  Created by IntelliJ IDEA.
  User: zwxu
  Date: 2018/12/11
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
User ID: ${user.userId}<br />
Username: ${user.username} (${user.username.length()} characters)<br />
<%--负略XML文本--%>
Full Name: ${fn:escapeXml(user.lastName) += ', '
        += fn:escapeXml(user.firstName)}
<br /><br />
<b>Permissions (${fn:length(user.permissions)})</b><br />
User: ${user.permissions["user"]}<br />
Moderator: ${user.permissions["moderator"]}<br />
Administrator: ${user.permissions["admin"]}<br />
</body>
</html>
