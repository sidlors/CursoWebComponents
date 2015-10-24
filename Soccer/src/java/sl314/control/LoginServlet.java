/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl314.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sl314.excepciones.SoccerException;

/**
 *
 * @author juanmanuel
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text / html");
        PrintWriter out = response.getWriter();
        out.println("<html><head></head><body>");
        String userInit=
              this.getServletContext().getInitParameter("userInitContextParam");
        String passInit=
              this.getServletContext().getInitParameter("passInitContextParam");
        HttpSession session = request.getSession(false);
        if (session != null) {
            if(userInit.equals(request.getParameter("user"))
                    &&
               passInit.equals(request.getParameter("pass"))){
                
                session = request.getSession();
                request
                        .getRequestDispatcher("index.jsp")
                        .forward(request, response);
                
            }else{
                try{
                    throw new SoccerException("Usuario no valido");
                } catch (SoccerException ex) {
                   out.println("<h1>Login invalido</h1>");
                   out.println("<a href='login.jsp'>login</a>");
                }
            }
        } else {
            out.println("there was a session!");
            
        }
        out.println("</body></html>");
    }

}
