<%@tag body-content="scriptless" %>
<%@attribute name="name" required="true" rtexprvalue="true" %>
<jsp:doBody var="message"/>

<%String bc = (String)jspContext.getAttribute("message");%>
<%=bc.toUpperCase()%>