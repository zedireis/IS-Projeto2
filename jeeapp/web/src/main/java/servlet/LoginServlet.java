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

import beans.IBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IBusiness  ibusiness;

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String email = request.getParameter("email");
        String key = request.getParameter("password");

        String destination = "/error.jsp";

        if (email != null && key != null) {
            boolean auth = false;
            auth = ibusiness.loginValidation(email,key);

            if (auth) {
                int id = ibusiness.getUserId(email);
                request.getSession(true).setAttribute("auth", id);
                request.getSession().setAttribute("manager", ibusiness.isManager(id));//NEW
                destination = "/secured/mainPage.jsp"; //VER PAG 69 DO LIVRO, CENA DO SECURE
                response.sendRedirect("/web/secured/mainPage.jsp");
            } else {
                request.getSession(true).removeAttribute("auth");
                request.getSession(true).removeAttribute("manager");
                request.setAttribute("erro","Falha na autenticação!");
                request.getRequestDispatcher(destination).forward(request, response);
            }
        }else{
            request.setAttribute("erro","Falha na autenticação!");
            request.getRequestDispatcher(destination).forward(request, response);
        }

    }

}