<%-- 
    Document   : error
    Created on : 26/09/2015, 11:52:17 AM
    Author     : juanmanuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  isErrorPage="true"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        out.println("Ocurrio un error inesperado: ");
        
        out.println(exception.toString());
        
        %>
        
    </body>
</html>
