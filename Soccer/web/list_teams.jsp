<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="soccer" uri="WEB-INF/tlds/soccer.tld"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <%@ include  file="template/header.jsp" %>
    <title>Duke's Soccer Team: List of Teams</title>
    <body>
         <table border="1">
            <thead>
                <tr>
                    <th>idTeam</th>
                    <th>name</th>
                    <th>location</th>
                    <th>fundation</th>
                    <th>status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${soccer:retriveTeams(pageContext)}" var="team">
                    <tr>
                        <td><c:out value="${team.idTeam}"/> </td>
                        <td><c:out value="${team.name}"/> </td>
                        <td><c:out value="${team.location}"/> </td>
                        <td><c:out value="${team.fundation}"/> </td>
                        <td><c:out value="${team.status}"/> </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <a href="admin/add_team.html">
            register a new team
        </a>
    </body>
    <%@ include  file="template/footer.jsp" %>
</html>
