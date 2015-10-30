package sl314.control;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.PageContext;
import sl314.persistencia.League;
import sl314.persistencia.Team;
import sl314.persistencia.Tournament;

/**
 *
 * @author jhernandezn
 */
public class ControlRetriveObjectsDB {

    public static List retriveLeagues(PageContext ctx) {
        List<League> listLeague = new ArrayList<League>();

        listLeague = (List<League>) ctx.findAttribute("collectionLeagues");
        return listLeague;
    }

    public static List retriveTeams(PageContext ctx) {

        List<Team> listTeams = new ArrayList<Team>();
        listTeams = (List<Team>)ctx.findAttribute("collectionTeams");
        return listTeams;
    }

    public static List retriveTournaments(PageContext ctx) {

        List<Tournament> listTournaments = new ArrayList<Tournament>();
        listTournaments = (List<Tournament>)ctx.findAttribute("collectionTournaments");
        return listTournaments;
    }
}
