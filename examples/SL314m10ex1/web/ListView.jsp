<%-- 
    Document   : ListView
    Created on : Oct 8, 2009, 10:16:46 PM
    Author     : Simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Airplane List</title>
    </head>
    <body>
        <h1>Airplanes</h1>
        <ul>
            <c:forEach var="airplane" items="${airplaneList}">
                <li>
                    Airplane id = ${airplane.id} is a ${airplane.type} with ${airplane.engines} engines
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
