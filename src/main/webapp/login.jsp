<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 24.12.2016
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Форма входа в систему</title>
</head>
<body>
<br>
<h1>Вход в систему</h1>
<form action="Registration" method="post">
    Пользователь: <input type="text" name="user" size="10"><br>
    Пароль: <input type="password" name="password" size="10"><br>
    <p>
    <table>
        <tr>
            <th><small>
                <input type="submit" name="login" value="Войти в систему">
            </small>
            <th><small>
                <input type="submit" name="registration" value="Зарегистрироваться">
            </small>
    </table>
</form>
<br>
</body>
</html>
