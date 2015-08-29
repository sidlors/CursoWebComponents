<%-- 
    Document   : welcome
    Created on : Oct 19, 2009, 4:50:18 PM
    Author     : Simon
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome
            <c:if test="${user != null}">
                ${user}
            </c:if>
            !
        </h1>
    </body>
</html>
