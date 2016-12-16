<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header navbar-brand"><spring:message code="app.title"/></div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form:form class="navbar-form" role="form" action="spring_security_check"
                               method="post">
                        <a href="oauth/github/authorize" role="button" class="btn btn-social btn-github">
                            <span class="fa fa-github"></span>
                            <spring:message code="app.login.github"/>
                        </a>
                        <div class="form-group">
                            <input type="text" placeholder="Email" class="form-control" name='username'>
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" class="form-control" name='password'>
                        </div>
                        <button type="submit" class="btn btn-success"><spring:message code="app.login"/></button>
                    </form:form>
                </li>
                <li>
                    <jsp:include page="fragments/lang.jsp"/>
                </li>
                <li>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="jumbotron">
    <div class="container">
        <c:if test="${error}">
            <div class="error">
                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="message">
                <spring:message code="${message}"/>
            </div>
        </c:if>
        <p>

        <p>User login: <b>user@yandex.ru / password</b></p>

        <p>Admin login: <b>admin@gmail.com / admin</b></p>

        <p><a class="btn btn-primary btn-lg" role="button" href="register"><spring:message
                code="app.register"/> &raquo;</a></p>
        <p>Стек технологий: <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
            <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
            <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
            <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security
                Test</a>,
            <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
            <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
            <a href="http://www.slf4j.org/">SLF4J</a>,
            <a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
            <a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
            <a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
            <a href="http://tomcat.apache.org/">Apache Tomcat</a>,
            <a href="http://www.webjars.org/">WebJars</a>,
            <a href="http://datatables.net/">DataTables plugin</a>,
            <a href="http://ehcache.org">Ehcache</a>,
            <a href="http://www.postgresql.org/">PostgreSQL</a>,
            <a href="http://junit.org/">JUnit</a>,
            <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,
            <a href="http://jquery.com/">jQuery</a>,
            <a href="http://ned.im/noty/">jQuery notification</a>,
            <a href="http://getbootstrap.com/">Bootstrap</a>.</p>
            <a href="https://developer.github.com/v3/oauth/">GitHub OAuth2</a>,
    </div>
</div>
<div class="container">
    <div class="lead">
        &nbsp;&nbsp;&nbsp;<a href="https://github.com/JavaOPs/topjava">Java Enterprise project</a> with
        registration/authorization and role-based interface (USER, ADMIN).
        Administrator can create/edit/delete users, user can manage his
        profile and data (day, food, calories) through UI (by AJAX) and REST interface with basic authorization.
        Data can be filtered by date and time, herewith color of food table entry depends on whether
        the amount of calories exceeds the normal day amount(editable parameter in user profile).
        All REST interface is covered by JUnit tests, using Spring MVC Test and Spring Security Test.
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>