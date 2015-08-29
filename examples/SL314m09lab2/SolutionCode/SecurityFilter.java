/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebFilter(filterName="SecurityFilter", urlPatterns={"/ApplicationController"}, dispatcherTypes={DispatcherType.REQUEST})
public class SecurityFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public SecurityFilter() {
    } 

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
	throws IOException, ServletException {
        ServletContext sc = filterConfig.getServletContext();
        sc.log("In filter");
        boolean OK = true; // assume true unless shown otherwise
        String operation = request.getParameter("operation");
        if ("Quote".equals(operation)) {
            sc.log("operation is Quote");
            String user = null;
            HttpSession session = null;
            if (request instanceof HttpServletRequest) {
                sc.log("request is a servlet request");
                HttpServletRequest hr = (HttpServletRequest)request;
                session = hr.getSession();
                if (session != null) {
                    sc.log("Found a session");
                    user = (String) session.getAttribute("user");
                    if (user == null || user.length() < 1) {
                        sc.log("didn't find a user");
                        OK = false;
                    } else { sc.log("found a user: " + user); }
                }
            } else { sc.log("filter request is not a servlet request"); }
        }
        if (OK) {
            sc.log("Doing a normal chain");
            chain.doFilter(request, response);
        } else {
            sc.log("Going for a redirect");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }

    /**
     * Destroy method for this filter 
     */
    public void destroy() { 
    }

    /**
     * Init method for this filter 
     */
    public void init(FilterConfig filterConfig) { 
	this.filterConfig = filterConfig;
    }
}
