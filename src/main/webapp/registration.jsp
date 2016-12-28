<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 24.12.2016
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Форма регистрации посетителей</title>
</head>
<body>
<h1>Регистрация посетителей</h1>
<form action="AddUser" method="post">
    Пользователь: <input type="text" name="user" size="10"><br>
    Пароль: <input type="password" name="password" size="10"><br>
    Email: <input type="text" name="email"><br>
    Телефон: <input type="text" name="phone"><br>
    <p>
    <table>
        <tr>
            <th><small>
                <input type="submit" name="save" value="Сохранить">
            </small>
            <th><small>
                <input type="submit" name="cancel" value="Выйти">
            </small>
    </table>
</form>
</body>
</html>
