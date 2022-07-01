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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.parseInt;


@WebServlet("/secured/deletetrip")
public class DeleteTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IBusiness  ibusiness;

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<String> lista;
        lista = ibusiness.getUnstartedTrips();

        request.setAttribute("myListOfTrips", lista);

        request.getRequestDispatcher("/secured/deleteTrip.jsp").include(request, response);
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String tripid = request.getParameter("tripid");

        if (tripid != null ) {
            try {
                int id = parseInt(tripid);
                if (ibusiness.deleteTrip(id)){
                    List<String> lista;
                    lista = ibusiness.getUnstartedTrips();

                    request.setAttribute("myListOfTrips", lista);
                    out.println("Viagem eliminada!");
                }else{
                    out.println("Verifique o ID -> Viagem não existe ou já começou!");
                    List<String> lista;
                    lista = ibusiness.getUnstartedTrips();

                    request.setAttribute("myListOfTrips", lista);
                }

                request.getRequestDispatcher("/secured/deleteTrip.jsp").include(request, response);
            }catch (NumberFormatException e){
                out.println("Por favor insira um ID válido!");
                List<String> lista;
                lista = ibusiness.getUnstartedTrips();

                request.setAttribute("myListOfTrips", lista);
                request.getRequestDispatcher("/secured/deleteTrip.jsp").include(request, response);
            }
        }
        else{
            out.println("Por favor insira um ID!");
            request.getRequestDispatcher("/secured/deleteTrip.jsp").include(request, response);
        }

        out.close();
    }
}