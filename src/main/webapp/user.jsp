<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 24.12.2016
  Time: 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пользователь</title>
</head>
<body>
<c:forEach var="User" items="${Users}">
    <tr>
        <td>${User.name}</td>
    </tr>
    </c>
</c:forEach>
</body>
</html>
