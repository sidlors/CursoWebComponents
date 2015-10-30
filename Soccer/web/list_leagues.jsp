<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="soccer" uri="WEB-INF/tlds/soccer.tld"%>
<html>
    <title>Duke's Soccer League: List Leagues</title>
    <jsp:include page="template/header.jsp"/>
    <body>
        <p>
            The current soccer league is:
        </p>
        <b>${applicationScope.currentLeague.name}</b>  <b>${applicationScope.currentLeague.year}</b>
        <br/><br/>
        <table border="1">
            <thead>
                <tr>
                    <th>ID_LEAGUE</th>
                    <th>NAME</th>
                    <th>YEAR</th>
                    <th>STATUS</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${soccer:retriveLeagues(pageContext)}" var="league">
                    <tr>
                        <td><c:out value="${league.idLeague}"/> </td>
                        <td><c:out value="${league.name}"/> </td>
                        <td><c:out value="${league.year}"/> </td>
                        <td><c:out value="${league.status}"/> </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <a href="admin/add_league.html">
            register a new League
        </a>
    </body>
    <jsp:include page="template/footer.jsp"/>
</html> 