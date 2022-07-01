package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.TripsAvailable;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/secured/*")
public class LoginFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(TripsAvailable.class);
    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain
            chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        logger.info("Requested access to secured page:"+req.getRequestURI());

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpSession session = httpReq.getSession(false);
        if (session != null && session.getAttribute("auth") != null)
        {
            chain.doFilter(request, response);
        }
        else
        {
            request.setAttribute("erro","Falha na autenticação!\nTentou aceder a uma página segura!");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}