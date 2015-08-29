<%-- 
    Document   : advertize
    Created on : Oct 19, 2009, 3:42:50 PM
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
        <h1>
            <c:if test="${user != null}">
                Hi ${user}!
            </c:if>
            Let me sell you something!</h1>
        <ul>
            <li>It's cool!</li>
            <li>Your friends will whisper about you if you don't have it.
            Then might even go suddenly silent when you walk in.</li>
            <li>You'll be ignored by the opposite gender if you don't have it</li>
            <li>You'll feel so good when you finally have it</li>
            <li>What, you're worried about the cost? Ooh dear...</li>
            <li>You know that feeling that something is wrong with your life? It's because you don't have this.</li>
            <li>Sure, you could make do with the love of friends and family, but hey, this is the 21 century!</li>
        </ul>
    </body>
</html>
