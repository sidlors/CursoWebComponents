<%-- 
    Document   : index
    Created on : Oct 19, 2009, 8:46:23 AM
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
        <h1>Hello Bank Customer!</h1>
        <form action="doATM">
            <p>Operation:
                <select name="operation">
                    <option>Deposit</option>
                    <option>Withdraw</option>
                </select>
            </p>
            <p>Amount:
                <input type="text" name="amount"/>
            </p>
            <p><input type="submit"/></p>
        </form>
        <p>Your balance is: ${account.balance}</p>
    </body>
</html>
