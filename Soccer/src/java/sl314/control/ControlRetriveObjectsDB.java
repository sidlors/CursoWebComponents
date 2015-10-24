package sl314.control;

import java.util.ArrayList;
import java.util.List;
import sl314.persistencia.League;
import sl314.persistencia.Team;
import sl314.persistencia.Tournament;

/**
 *
 * @author jhernandezn
 */
public class ControlRetriveObjectsDB {
    
    
    
    public static List retriveLeagues() {
        
        ArrayList<League> listLeague=new ArrayList<League>();
//        LeagueJpaController LeagueDAO=new LeagueJpaController(null, null);
//        listLeague=(ArrayList<League>) LeagueDAO.findLeagueEntities();
        League liga=new League();
        liga.setIdLeague(29);
        liga.setName("Liga de prueba");
        liga.setStatus(0);
        liga.setYear(2016);
        listLeague.add(liga);
        return listLeague;
    }
    
    public static List retriveTeams() {
        
        ArrayList<Team> listTeams=new ArrayList<Team>();
//        LeagueJpaController LeagueDAO=new LeagueJpaController(null, null);
//        listLeague=(ArrayList<League>) LeagueDAO.findLeagueEntities();
        Team team=new Team();
        team.setIdTeam(1);
        team.setName("Ámerica de Cali");
        team.setLocation("Colombia");
        team.setStatus(Boolean.TRUE);
        team.setFundation(2016);
        listTeams.add(team);
        return listTeams;
    }
    
    
        public static List retriveTournaments() {
        
        ArrayList<Tournament> listTournaments=new ArrayList<Tournament>();
//        LeagueJpaController LeagueDAO=new LeagueJpaController(null, null);
//        listLeague=(ArrayList<League>) LeagueDAO.findLeagueEntities();
        Tournament tournament=new Tournament();
        tournament.setDivision("1era division");
        tournament.setIsProfessional(Boolean.TRUE);
        tournament.setTeamCollection(null);
        listTournaments.add(tournament);
        return listTournaments;
    }
}
