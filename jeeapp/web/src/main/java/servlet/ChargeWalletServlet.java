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
import data.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/secured/ChargeWallet")
public class ChargeWalletServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IBusiness  ibusiness;

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userid = (int) request.getSession().getAttribute("auth");
        request.setAttribute("saldo", ibusiness.getSaldo(userid));

        request.getRequestDispatcher("/secured/chargewallet.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = (int) request.getSession().getAttribute("auth");

        String valor = request.getParameter("valor");

        String destination = "/secured/chargewallet.jsp";

        int ivalor = Integer.parseInt(valor);
        if (ivalor > 0) {
            ibusiness.CarregaCarteira(id, ivalor);
        }else{
            out.println("Valor inválido!");
        }
        request.setAttribute("saldo", ibusiness.getSaldo(id));
        request.getRequestDispatcher(destination).include(request, response);

        out.close();
    }

}