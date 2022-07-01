package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.IBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/secured/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        logger.info("LOGOUT");

        HttpSession session=request.getSession();
        session.removeAttribute("auth");
        session.removeAttribute("manager");//NEW

        //session.getMaxInactiveInterval();

        response.sendRedirect("/web/index.jsp");

    }

}