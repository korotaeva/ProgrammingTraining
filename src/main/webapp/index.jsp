<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 23.12.2016
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Форма входа в систему</title>
  <style type="text/css">
    .auth{
      width: 250px;
      height: 250px;
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      margin: auto;
    }
    .parent{
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      overflow: auto;
    }
  </style>
</head>
<body>
<br>


  <div class="parent">
  <div class="auth">
    <h1>Вход в систему</h1>
    <form action="authorization" method="post">
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
    </div>
  </div>

<br>
</body>
</html>
