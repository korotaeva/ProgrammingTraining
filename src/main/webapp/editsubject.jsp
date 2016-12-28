<%--
  Created by IntelliJ IDEA.
  User: korot
  Date: 28.12.2016
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Материал</title>
</head>
<style type="text/css">
    <%@include file="/css/jquery.cleditor.css"%>
</style>
<script >
    <%@include file="/js/jquery-1.8.2.min.js"%>
</script >
<script >
    <%@include file="/js/jquery.cleditor.min.js"%>
</script >

<script>
    $(document).ready(function () { $("#description").cleditor(); });
</script>

<body>

<h1>Материал</h1>


<form method="post"
      action="${pageContext.request.contextPath}/subject/save">
    <fieldset>
        <legend>
            <c:choose>
                <c:when test="${not empty subject.id }">
                    Обновить тему
                </c:when>
                <c:otherwise>
                    Добавить тему
                </c:otherwise>
            </c:choose>
        </legend>

        <div>
            <label for="name">Тема</label> <input type="text" name="name"
                                                    id="name" value="${subject.name}" />
        </div>

        <div  valign="top">
            <label for="description">Описание</label>
            <textarea name="description" id="description" rows="60" cols="60">${subject.description}</textarea>
        </div>



        <c:if test="${not empty subject.id}">
            <input type="hidden" name="id" value="${subject.id}" />
        </c:if>

    </fieldset>

    <div class="button-row">
        <a href="${pageContext.request.contextPath}/subject/">Отмена</a> or <input type="submit" value="Сохранить" />
    </div>
</form>

</body>
</html>