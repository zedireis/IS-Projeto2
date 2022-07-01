package servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.IBusiness;
import data.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/secured/searchsorted")
public class SearchSorted extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Logger logger = LoggerFactory.getLogger(TripsAvailable.class);
    @EJB
    private IBusiness  ibusiness;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<String> lista;

        String dateInicio = request.getParameter("dateInicio");
        String dateFim = request.getParameter("dateFim");
        String horaInicio = request.getParameter("horaPartida");
        String horaFim = request.getParameter("horaChegada");

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(dateInicio.equals("") || dateFim.equals("") || horaFim.equals("") || horaInicio.equals("")){
            out.print("Por favor insira uma data e hora!");
        }else {

            String dataPartida = dateInicio+ " " + horaInicio + ":00";
            String dataChegada = dateFim+ " " + horaFim + ":00";

            try {
                Date dateI=formatter.parse(dataPartida);
                Date dateF=formatter.parse(dataChegada);

                if (dateI.after(dateF)) {
                    out.print("Por favor insira uma data válida!");
                }else {


                    lista = ibusiness.tripsAvailablesSorted(dateI, dateF);
                    request.setAttribute("myListOfTripsSorted", lista);

                    Map<Integer, String> lista2 = ibusiness.getTripsByDate(dateI, dateF);
                    if(lista2.containsKey(-1)){
                        out.println("Sem viagens para o dia selecionado!");
                    }else {
                        request.setAttribute("listDestinos", lista2);
                    }
                }

            } catch (ParseException e) {
                out.print("Por favor insira uma data válida!");
            }

        }
        request.getRequestDispatcher("/secured/SearchSorted.jsp").include(request, response);
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
            request.getRequestDispatcher("/secured/SearchSorted.jsp").include(request, response);
        }

        out.close();
    }

}