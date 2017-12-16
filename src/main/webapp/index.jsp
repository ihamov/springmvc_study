<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index Page</title>
</head>
<body>
    <h2>Hello World!</h2>
    <br><br>


    <form action="springmvc/testPojo" method="post">
        username:<input type="text" name="username">
        <br>
        password:<input type="password" name="password">
        <br>
        email:<input type="text" name="email">
        <br>
        age:<input type="text" name="age">
        <br>
        city:<input type="text" name="address.city">
        <br>
        province:<input type="text" name="address.province">
        <br>
        <input type="submit" value="submit">
    </form>
    <br><br>

    <a href="springmvc/testCookieValue">Test CookieValue</a>
    <br><br>

    <a href="springmvc/testRequestHeader">Test RequestHeader</a>
    <br><br>

    <a href="springmvc/testRequestParam?username=du&age=11">Test RequestParam</a>
    <br><br>

    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="Test Rest PUT">
    </form>
    <br><br>

    <form action="springmvc/testRest/1" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="Test Rest DELETE">
    </form>
    <br><br>

    <form action="springmvc/testRest" method="post">
        <input type="submit" value="Test Rest POST">
    </form>
    <br><br>

    <a href="springmvc/testRest/1">Test Rest GET</a>
    <br><br>

    <a href="springmvc/testPathVariable/1">Test PathVariable</a>
    <br><br>

    <a href="springmvc/testAntAndPath/ttt/abc">Test AntAndPath</a>
    <br><br>

    <a href="springmvc/testParamsAndHeaders?username=du&age=10">Test ParamsAndHeaders</a>
    <br><br>

    <form action="springmvc/testMethod" method="post">
        <input type="submit" value="Submit">
    </form>
    <br>

    <a href="springmvc/testMethod">Test Method</a>
    <br><br>

    <a href="springmvc/testRequestMapping">Test RequestMapping</a>
    <br><br>

    <a href="helloworld">Hello World</a>
</body>
</html>
