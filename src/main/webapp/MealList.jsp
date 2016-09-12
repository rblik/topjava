<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal List</title>
    <style>
        table {
            width: 400px;
        }

        table, td, th, tr {
            background-color: transparent;;
            border: 1px solid black;
            border-collapse: collapse;
            text-align: center;
        }

        .red {
            color: red;
        }

        .green {
            color: green;
        }

        .header {
            background-color: aqua;
        }
    </style>
</head>
<body>
<table>
    <tr class="header">
        <th>ID</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Date</th>
        <th>Edit</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr class="${(meal.exceed == true)? 'red' : 'green'}">
            <c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}"/>
            <td>${meal.id}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${cleanedDateTime}</td>
            <td><a href="edit?id=${meal.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
