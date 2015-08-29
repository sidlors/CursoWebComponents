<%-- 
    Document   : ExceptionView
    Created on : Oct 8, 2009, 10:23:12 PM
    Author     : Simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Something went wrong</title>
    </head>
    <body>
        <h1>That broke</h1>
        Here's the error report<br>
        ${exception}
    </body>
</html>
