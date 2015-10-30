
package sl314.listeners;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sl314.control.AddLeagueServlet;
import sl314.model.LeagueDTO;
import sl314.persistencia.League;
import sl314.persistencia.Team;
import sl314.persistencia.Tournament;


public class SoccerApplicationListener implements ServletContextListener {
    
    
    @PersistenceContext
    private  EntityManager em;
    @Resource
    private UserTransaction utx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Se inicia Soccer Web App");
        String leagueName=sce.getServletContext().getInitParameter("currentLeagueName");
        String yearLeage=sce.getServletContext().getInitParameter("currentYear");
        
        int year=Integer.parseInt(yearLeage);
        LeagueDTO currentLeague=new LeagueDTO(year, leagueName);
        sce.getServletContext().setAttribute("currentLeague",currentLeague);
        
        setLeaguesToContext(sce);
        setTeamsToContext(sce);
        setTournamentsToContext(sce);
    }
    
    
    private void setLeaguesToContext(ServletContextEvent sce){
    
     try {
             Query query = em.createQuery("SELECT l FROM League l");
             Collection<League> collectionLeagues=(Collection<League>) query.getResultList();
             sce.getServletContext().setAttribute("collectionLeagues",collectionLeagues);
            for(League league:collectionLeagues){
                System.out.println(league.getIdLeague() +" "+league.getName() );
            }
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }
    
    }
    
    
    private void setTeamsToContext(ServletContextEvent sce){
    
     try {
             Query query = em.createQuery("SELECT t FROM Team t");
             Collection<Team> collectionTeams=(Collection<Team>) query.getResultList();
             sce.getServletContext().setAttribute("collectionTeams",collectionTeams);
            for(Team team:collectionTeams){
                System.out.println(team.getIdTeam() +" "+team.getName() );
            }
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }
    
    }
    
    private void setTournamentsToContext(ServletContextEvent sce){
    
     try {
             Query query = em.createQuery("SELECT t FROM Tournament t");
             Collection<Tournament> collectionTournaments=(Collection<Tournament>) query.getResultList();
             sce.getServletContext().setAttribute("collectionTournaments",collectionTournaments);
            for(Tournament tournament:collectionTournaments){
                System.out.println(tournament.getIdTournament() +" "+tournament.getDivision());
            }
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SecurityException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SystemException ex1) {
                Logger.getLogger(AddLeagueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }
    
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Se cierra Soccer Web App");
    }
}
