package servlet;

import beans.IBusiness;
import exceptions.InvalidTripId;
import exceptions.NotEnoughMoneyException;
import exceptions.TripFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;


@WebServlet("/secured/searchbydate")
public class SearchByDate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Logger logger = LoggerFactory.getLogger(SearchByDate.class);
    @EJB
    private IBusiness  ibusiness;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Map<Integer, String> lista;


        String data = request.getParameter("dateInicio");


        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date dateI = formatter.parse(data);
            lista = ibusiness.getTripsByDate(dateI,null);
            if(lista.containsKey(-1)){
                out.println("Sem viagens para o dia selecionado!");
            }else {
                request.setAttribute("listDestinos", lista);
            }
        } catch (ParseException e) {
            out.print("Por favor insira uma data válida!");
        }

        request.getRequestDispatcher("/secured/searchByDate.jsp").include(request, response);
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("viagem");


        if(id!=null) {
            int tripid = Integer.parseInt(id);


            List<String> lista = ibusiness.getTicketsForTrip(tripid);

            request.setAttribute("myListOfTrips", lista);

            request.getRequestDispatcher("/secured/passengerList.jsp").forward(request, response);


        }else{
            out.println("Escolha uma viagem!");
            request.getRequestDispatcher("/secured/searchByDate.jsp").include(request, response);
        }

        out.close();
    }

}