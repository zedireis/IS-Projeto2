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


@WebServlet("/Registo")
public class RegistoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IBusiness  ibusiness;

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String destination = "/error.jsp";

        if (email != null && name != null && password != null) {
            boolean registado = ibusiness.addUser(email, name,password);
            if(registado) {
                destination = "/index.jsp";
                logger.info("Utilizador->"+email+" registado com sucesso!");
            }else{
                request.setAttribute("erro","Utilizador já existente");
                request.getRequestDispatcher(destination).forward(request, response);
                logger.error("[ERRO AO REGISTAR] Utilizador->"+email+" já existente!");
            }
        }
        else{
            System.out.println("Erro ao registar");
        }


        request.getRequestDispatcher(destination).forward(request, response);
    }

}