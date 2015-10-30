/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl314.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sl314.model.LeagueDTO;
import sl314.persistencia.League;

/**
 *
 * @author juanmanuel
 */
@WebServlet(name = "AddLeagueServlet", urlPatterns = {"/admin/AddLeagueServlet.do"})
public class AddLeagueServlet extends HttpServlet {

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
        
        String strYear = request.getParameter("year");
        String name=request.getParameter("name");
        Collection<League> collectionLeagues=(Collection<League>)request.getServletContext().getAttribute("collectionLeagues");
                //setAttribute("collectionLeagues",collectionLeagues);
        int year=0;
        try {
            year = Integer.parseInt(strYear);
        } catch (NumberFormatException nfe) {
            errorMsgs.add("The 'year' field must be a positive integer.");
        }

        if (!errorMsgs.isEmpty()) {
            RequestDispatcher view = request.getRequestDispatcher("error_page.jsp");
            view.forward(request, response);
            return;
        }
        

        League league = new League();

        league.setName(name);
        league.setYear(year);
       
        try {
            utx.begin();
            em.persist(league);
            utx.commit();
            collectionLeagues.add(league);
            request.getServletContext().setAttribute("collectionLeagues",collectionLeagues);
            
            
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
        request.setAttribute("league", league);
        // Send the Success view
        RequestDispatcher view = request.
                getRequestDispatcher("../success.jsp");
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
