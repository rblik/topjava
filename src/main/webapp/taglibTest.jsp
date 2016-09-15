<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="mylibs" uri="http://ru/javawebinar/topjava/mytags" %>
<html>
<head>
    <title>TagLibTest</title>
</head>
<body>
<mylibs:add op1="${param.a}" op2="${param.b}"/><br>
<mylibs:method type="method"/><br>
<mylibs:cap>Hello World</mylibs:cap><br>
<mylibs:greeting name="ek0">Hello</mylibs:greeting><br>
${mylibs:leetTalk("My appauling programming skills")}
</body>
</html>
