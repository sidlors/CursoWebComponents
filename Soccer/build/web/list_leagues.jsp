<html>
    
    <title>Duke's Soccer League: List Leagues</title>
    <body>
        <!-- Page Heading -->
        <table border='1' cellpadding='5' cellspacing='0' width='400'>
            <tr bgcolor='#CCCCFF' align='center' valign='center' height='20'>
                <td><h3>Duke's Soccer League: List Leagues</h3></td>
            </tr>
        </table>
        <p>
            The current soccer league is:
        </p>
        
        <h3>Current League:</h3>
        <b>${applicationScope.currentLeague.name}</b>
        <br/>
        <h3>Current Year:</h3>
        <b>${applicationScope.currentLeague.year}</b>
        <br/><br/>
        <%--
        // hacer select * from Leagues ;
        --%>
        <a href="admin/add_league.html">
            register a new League
        </a>
    </body>
</html> 