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
<form onsubmit="return checkForm(this)" action="${pageContext.servletContext.contextPath}/authorization" method="post">
    Пользователь: <input type="text" name="user" id="user" size="10"><br>
    <p id='err_user' style="color: red;"  class='error'></p>
    Пароль: <input type="password" name="password" id="password" size="10"><br>
    <p id='err_password' style="color: red;"  class='error'></p>
    Email: <input type="text" name="email" id="email"><br>
    <p id='err_email' style="color: red;"  class='error'></p>
    Телефон: +7<input type="text" name="phone" id="phone"><br>
    <p id='err_phone' style="color: red;"  class='error'></p>
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
<script >
    <%@include file="/js/jquery-1.8.2.min.js"%>
</script >
<script type="text/javascript">
    function clearVal(val, limit){
        var newVal = val.replace(/[^\d]+/g, '');
        if( newVal == '' ){
            return false;
        }else{
            return newVal.substring(0, limit);
        }
    }

    function getResString(newVal){
        var res = '';
        for(var i = 0; i < newVal.length; i++){
            if( i == 3 ){
                res += ' ';
                res += newVal.charAt(i);
            }else if( i == 6 || i == 8 ){
                res += '-';
                res += newVal.charAt(i);
            }else{
                res += newVal.charAt(i);
            }
        }
        return res;
    }

    $(function(){
        $('#phone').on('input', function(){
            var val = $(this).val(),
                    limit = 10;
            if( val == '' ) return;

            var newVal = clearVal(val, limit);
            if(!newVal){
                $(this).val('');
                return;
            }
            var res = getResString(newVal);
            $(this).val(res);
        });
    });


    function checkForm(form){
        var error = false;
        if (document.getElementById('user').value=="") {
            document.getElementById('err_user').innerHTML='Введите имя';
            error = true;
        } else{
            document.getElementById('err_user').innerHTML = '';
        };
        if (document.getElementById('password').value=="") {
            document.getElementById('err_password').innerHTML='Введите пароль';
            error = true;
        } else{
            document.getElementById('err_password').innerHTML="";
        };
        if (document.getElementById('email').value=="") {
            document.getElementById('err_email').innerHTML='Введите email';
            error = true;
        } else{
            document.getElementById('err_email').innerHTML = "";
            var r = /^[\w\.\d-_]+@[\w\.\d-_]+\.\w{2,4}$/i;
            if (!r.test(document.getElementById('email').value)) {
                document.getElementById('err_email').innerHTML='Некорректный email';
                error = true;
            }
        };
        if (document.getElementById('phone').value=="") {
            document.getElementById('err_phone').innerHTML='Введите телефон';
            error = true;
        } else{
            document.getElementById('err_phone').innerHTML = "";
        };
        if(error == true){
            return false;
        }
        generateSalt();
        return true;

    };



</script>

</body>
</html>
