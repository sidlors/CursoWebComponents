package sl314.model;


public class TeamDTO {
  
    private String name;
    private int fundation;
    private String location;
    private String status;
    private LeagueDTO league;
    private TournamentDTO tournament;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFundation() {
        return fundation;
    }

    public void setFundation(int fundation) {
        this.fundation = fundation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LeagueDTO getLeague() {
        return league;
    }

    public void setLeague(LeagueDTO league) {
        this.league = league;
    }

    public TournamentDTO getTournament() {
        return tournament;
    }

    public void setTournament(TournamentDTO tournament) {
        this.tournament = tournament;
    }
    
    

}
