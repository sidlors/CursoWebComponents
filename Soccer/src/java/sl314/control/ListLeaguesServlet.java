/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl314.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import sl314.model.LeagueDTO;

/**
 *
 * @author juanmanuel
 */
public class ListLeaguesServlet extends HttpServlet {

    List leagueList = null;

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
        PrintWriter out= response.getWriter();
        leagueList = new LinkedList();
        leagueList.add(new LeagueDTO(2003, "Soccer League (Spring 03)"));
        leagueList.add(new LeagueDTO(2003, "Summer Soccer Fest 2003"));
        leagueList.add(new LeagueDTO(2003, "Fall Soccer League (2003)"));
        leagueList.add(new LeagueDTO(2004, "Soccer League (Spring 04)"));
        leagueList.add(new LeagueDTO(2004, "The Summer of Soccer Love 2004"));
        leagueList.add(new LeagueDTO(2004, "Fall Soccer League (2004)"));
        request.setAttribute("leagueList", leagueList);
        Enumeration<String> e=getServletConfig().getInitParameterNames();
        while(e.hasMoreElements()){
            out.println("<br>param name = " + e.nextElement() + "<br>");
        }
        out.println("main email is " + getServletConfig().getInitParameter("mainMail"));
        out.println("<br>");
         
        
        
        
        
        
        
        
        
        
        
        
        

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
