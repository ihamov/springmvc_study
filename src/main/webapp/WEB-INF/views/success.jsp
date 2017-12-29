<%--
  Created by IntelliJ IDEA.
  User: Vigo
  Date: 2017/12/9
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Success Page</title>
</head>
<body>
    <H4>Success Page</H4>

    time:${requestScope.time}

    <br><br>
    names:${requestScope.names}

    <br><br>
    request user:${requestScope.user}
    <br><br>
    session user:${sessionScope.user}

    <br><br>
    request school:${requestScope.school}
    <br><br>
    session school:${sessionScope.school}
    <br><br>

    <fmt:message key="i18n.username"></fmt:message>
    <br><br>
    <fmt:message key="i18n.password"></fmt:message>
</body>
</html>
