/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Simon
 */
@WebServlet(name="ApplicationController", urlPatterns={"/ApplicationController"})
public class ApplicationController extends HttpServlet {
    private static final String [] quotes = {
      "Imagination is more important than knowledge.",
      "Any fool can make things bigger, more complicated, and more violent" +
              "It takes a touch of genius--and a lot of courage--to move in" +
              "the opposite direction.",
      "Sometimes one pays most for the things one gets for nothing.",
      "Common sense is the collection of prejudices acquired by age 18.",
      "If you are out to describe truth, leave elegance to the tailor.",
      "Education is what remains after one has forgotten everything learned" +
              " in school."
    };
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String operation = request.getParameter("operation");
        String user = null;

        RequestDispatcher rd = request.getRequestDispatcher("badOperation.jsp");

        HttpSession session = request.getSession();

        user = (String)session.getAttribute("user");
        request.setAttribute("user", user);

        if ("Quote".equals(operation)) {
            rd = request.getRequestDispatcher("quote.jsp");
            int quoteId = (int)(Math.random()*quotes.length);
            request.setAttribute("quote", quotes[quoteId]);
        } else if ("Advertise".equals(operation)) {
            rd = request.getRequestDispatcher("advertize.jsp");
        } else if ("Login".equals(operation)) {
            session.setAttribute("user", request.getParameter("user"));
            request.setAttribute("user", request.getParameter("user"));
            rd = request.getRequestDispatcher("welcome.jsp");
        }

        rd.forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
