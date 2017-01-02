<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 24.12.2016
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="j"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <title>Темы</title>
</head>

<a  valign="top" href="${pageContext.servletContext.contextPath}/logout">Выйти</a>

<h1>Список тем</h1>

<a href="${pageContext.servletContext.contextPath}/subject/create">Добавить тему</a>

<table border="1">
    <tr>
        <td>Тема</td>
    </tr>
    <j:forEach items="${Subjects}" var="Subject" varStatus="status">
        <tr valign="top">
            <td>${Subject.name}</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/subject/edit/${Subject.id}">Редактировать/Просмотр</a>
                <a href="${pageContext.servletContext.contextPath}/subject/delete/${Subject.id}">Удалить</a>
            </td>
        </tr>
    </j:forEach>
</table>
</body>
</html>
