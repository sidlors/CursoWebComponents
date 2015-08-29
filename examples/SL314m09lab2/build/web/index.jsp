<%-- 
    Document   : index
    Created on : Oct 19, 2009, 1:10:32 PM
    Author     : Simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
           <form action="ApplicationController">
            <p>Select an operation:
                <select name="operation">
                    <option>Quote</option>
                    <option>Advertise</option>
                </select>
            </p>
            <p><input type="submit"/></p>
        </form>

</html>
