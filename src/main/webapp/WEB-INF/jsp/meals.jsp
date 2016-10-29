<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/jquery.dataTables.min.css">
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <section>
                <h3><fmt:message key="meals.title"/></h3>
                <form method="post" class="form-horizontal" action="meals/filter">
                    <div class="form-group col-xs-12">
                        <label for="startDate" class="control-label col-xs-2"><fmt:message
                                key="meals.startDate"/>:</label>
                        <div class="col-xs-2">
                            <input type="date" name="startDate" class="form-control" id="startDate"
                                   value="${param.startDate}">
                        </div>
                        <label for="endDate" class="control-label col-xs-2"><fmt:message key="meals.endDate"/>:</label>

                        <div class="col-xs-2">
                            <input type="date" name="endDate" class="form-control" id="endDate"
                                   value="${param.endDate}">
                        </div>
                    </div>
                    <div class="form-group col-xs-12">
                        <label for="endDate" class="control-label col-xs-2"><fmt:message
                                key="meals.startTime"/>:</label>
                        <div class="col-xs-2">
                            <input type="date" name="endDate" class="form-control" id="startTime"
                                   value="${param.startTime}">
                        </div>
                        <label for="endDate" class="control-label col-xs-2"><fmt:message key="meals.endTime"/>:</label>
                        <div class="col-xs-2">
                            <input type="date" name="endDate" class="form-control" id="endTime"
                                   value="${param.endTime}">
                        </div>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-primary"><fmt:message
                                key="meals.filter"/></button>
                    </div>
                </form>
                <hr>
                <div>
                    <a href="meals/create" class="btn btn-info"><fmt:message key="meals.add"/></a>
                </div>
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="meals.dateTime"/></th>
                        <th><fmt:message key="meals.description"/></th>
                        <th><fmt:message key="meals.calories"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${meals}" var="meal">
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                            <td>
                                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                    <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                    ${fn:formatDateTime(meal.dateTime)}
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a href="meals/update?id=${meal.id}" class="btn btn-warning btn-xs"><fmt:message key="common.update"/></a></td>
                            <td><a href="meals/delete?id=${meal.id}" class="btn btn-danger btn-xs"><fmt:message key="common.delete"/></a></td>
                        </tr>
                    </c:forEach>
                </table>
            </section>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
