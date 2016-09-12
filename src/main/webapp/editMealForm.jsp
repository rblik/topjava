<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
    <style>
        form {
            margin-top: 15px;
            width: auto;
        }
        label {
            margin-left: 7px;
        }
        input {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
<label style="font-size: large; color: blue">Change meal properties</label>
<form action="meals" method="post" accept-charset="UTF-8">
    <input type="hidden" name="id" value="${meal.id}">
    <label for="description">Description</label><br><input id="description" type="text" name="description" value="${meal.description}"><br>
    <label for="calories">Calories</label><br><input id="calories" type="text" name="calories" value="${meal.calories}"><br>
    <label for="datetime">Datetime</label><br><input id="datetime" type="datetime-local" name="date" value="${meal.dateTime}"><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
