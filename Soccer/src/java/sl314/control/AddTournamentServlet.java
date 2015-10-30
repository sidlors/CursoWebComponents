package sl314.control;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sl314.persistencia.Tournament;

/**
 *
 * @author jhernandezn
 */
public class AddTournamentServlet extends HttpServlet {
    
    
    List errorMsgs = new LinkedList<Object>();   
    @PersistenceContext
    private  EntityManager em;
    @Resource
    private UserTransaction utx;


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String division = request.getParameter("division");
        String type=request.getParameter("type");
        Collection<Tournament> collectionTournaments=(Collection<Tournament>)request.getServletContext().getAttribute("collectionTournaments");
        if(type==null ){
            
            errorMsgs.add("category not selected");
        }

        if (!errorMsgs.isEmpty()) {
            RequestDispatcher view = request.getRequestDispatcher("soccer_error.jsp");
            request.setAttribute("errorMsgs", errorMsgs);
            view.forward(request, response);
            return;
        }
        

        Tournament tournament = new Tournament();

        tournament.setDivision(division);
        if(type.equals("Professional")){
        tournament.setIsProfessional(Boolean.TRUE);
        }else{
            tournament.setIsProfessional(Boolean.FALSE);
        }
       
        try {
            utx.begin();
            em.persist(tournament);
            utx.commit();
            collectionTournaments.add(tournament);
            request.getServletContext().setAttribute("collectionTournaments",collectionTournaments);
            
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
        // Store the new league in the request-scope
        request.setAttribute("tournament", tournament);
        // Send the Success view
        RequestDispatcher view = request.getRequestDispatcher("../success.jsp");
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
