<%@page import="java.util.List"%>
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
        List errorMsgs =(List) request.getAttribute("errorMsgs");
        for(Object error:errorMsgs){
            out.println(error.toString());
        }
        %>
        
    </body>
</html>
