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


@WebServlet("/secured/editinfo")
public class editInfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IBusiness  ibusiness;

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = (int) request.getSession(true).getAttribute("auth");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        //Quando uma text box não tem input, sai um string vazia "", nós queremos null
        if(name.length() == 0){
            name = null;
        }
        if(email.length() == 0){
            email = null;
        }
        if(password.length() == 0){
            password = null;
        }

        String destination = "/secured/userEditInfo.jsp";

        boolean registado = ibusiness.editUser(id, email,name,password);
        if(registado) {
            logger.info("[INFORMACAO EDITADA] Utilizador->"+id);
            destination = "/secured/mainPage.jsp";
        }else{
            out.println("Erro ao editar a informação: email já existe!");
            logger.error("[ERRO AO EDITAR] Utilizador->"+email+" já existente!");
        }

        request.getRequestDispatcher(destination).include(request, response);

        out.close();
    }

}