<%-- 
    Document   : login
    Created on : 19/09/2015, 01:10:49 PM
    Author     : juanmanuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Login</title>
    </head>
    <h1>Bienvenido Usuario</h1>
    <body>
        <form action="login.do" method="POST">
            user:<input type="text" name="user" value="" />
            <br/>
            pass:<input type="password" name="pass" value="" />
            <br/>
            <input type="submit" value="Aceptar" />
        </form>
    </body>
</html>
