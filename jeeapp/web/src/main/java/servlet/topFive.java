package servlet;

import beans.IBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/secured/topfive")
public class topFive extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Logger logger = LoggerFactory.getLogger(topFive.class);
    @EJB
    private IBusiness  ibusiness;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<String> lista;

        int userid = (int) request.getSession().getAttribute("auth");

        lista = ibusiness.getTopFive();
        request.setAttribute("myListOfTrips", lista);

        request.getRequestDispatcher("/secured/topFive.jsp").include(request, response);
        out.close();
    }

}