<%-- 
    Document   : quote
    Created on : Oct 19, 2009, 3:41:22 PM
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
        <h1><c:if test="${user != null}">
                Hi ${user}!
            </c:if>
            Here's a quotation!
        </h1>
    </body>
    ${quote}
</html>
