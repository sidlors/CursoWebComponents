<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="soccer" uri="WEB-INF/tlds/soccer.tld"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <%@ include  file="template/header.jsp" %>
    <title>Duke's Soccer Tournament: List Tournament</title>
    <body>
         <table border="1">
            <thead>
                <tr>
                    <th>idTournament</th>
                    <th>division</th>
                    <th>isProfessional</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${soccer:retriveTournaments()}" var="tournament">
                    <tr>
                        <td><c:out value="${tournament.idTournament}"/> </td>
                        <td><c:out value="${tournament.division}"/> </td>
                        <td><c:out value="${tournament.isProfessional}"/> </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <a href="admin/add_tournament.html">
            register a new tournament
        </a>
    </body>
    <%@ include  file="template/footer.jsp" %>
</html>
