<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index Page</title>
</head>
<body>
    <h2>Hello World!</h2>
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
