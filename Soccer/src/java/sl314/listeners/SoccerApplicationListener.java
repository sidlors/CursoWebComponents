
package sl314.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sl314.control.AddLeagueServlet;
import sl314.model.LeagueDTO;


public class SoccerApplicationListener implements ServletContextListener {
    
    
    @PersistenceContext
    private  EntityManager em;
    @Resource
    private UserTransaction utx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Se inicia Soccer Web App");
        String leagueName=sce.getServletContext().
                getInitParameter("currentLeagueName");
        String yearLeage=sce.getServletContext().
                getInitParameter("currentYear");
        
        int year=Integer.parseInt(yearLeage);
        LeagueDTO currentLeague=new LeagueDTO(year, leagueName);
        sce.getServletContext().
                setAttribute("currentLeague",currentLeague);
        
         try {
            utx.begin();
            
            utx.commit();
            
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
