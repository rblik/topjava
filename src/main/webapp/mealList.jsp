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
<a href="editMealForm.jsp">Add meal</a>

<table>
    <tr class="header">
        <th>ID</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Date</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr class="${(meal.exceed)? 'red' : 'green'}">
            <c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}"/>
            <td>${meal.id}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${cleanedDateTime}</td>
            <td><a href="edit?id=${meal.id}">Edit</a></td>
            <td><a href="meals?id=${meal.id}&remove">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
