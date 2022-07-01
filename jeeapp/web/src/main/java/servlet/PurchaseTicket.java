package servlet;

import beans.IBusiness;
import exceptions.InvalidTripId;
import exceptions.NotEnoughMoneyException;
import exceptions.TripFullException;
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


@WebServlet("/secured/purchaseticket")
public class PurchaseTicket extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Logger logger = LoggerFactory.getLogger(PurchaseTicket.class);
    @EJB
    private IBusiness  ibusiness;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<String> lista;

        List<String> listaDestinos = ibusiness.getDestinos();
        if(listaDestinos!=null) {
            request.setAttribute("listDestinos", listaDestinos);
        }

        String destino = request.getParameter("destino");
        if(destino!=null && destino.equals("")){
            destino=null;
        }

        String dateInicio = "";
        String horaInicio = "";
        String data = request.getParameter("dateInicio");
        if(data!=null){
            dateInicio=data;
        }
        String hora = request.getParameter("horaPartida");
        if(hora!=null){
            horaInicio=hora;
        }

        logger.info("Destino->"+destino+" Data e hora->"+dateInicio+" "+horaInicio);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(!horaInicio.equals("")){//Tem hora
            if(dateInicio.equals("")){//Sem dia
                out.print("Por favor insira uma data e hora!");
            }else{//Hora e dia
                String dataPartida = dateInicio + " " + horaInicio + ":00";
                try {
                    Date dateI = formatter.parse(dataPartida);
                    lista = ibusiness.getTripsToPurchase(dateI,destino);
                    request.setAttribute("myListOfTrips", lista);
                } catch (ParseException e) {
                    out.print("Por favor insira uma data válida!");
                }
            }
        }else{//Sem hora
            if(dateInicio.equals("")) {//Sem dia
                lista = ibusiness.getTripsToPurchase(null,destino);
                request.setAttribute("myListOfTrips", lista);
            }else {//Com dia
                String dataPartida = dateInicio + " 00:00:00";
                try {
                    Date dateI = formatter.parse(dataPartida);
                    lista = ibusiness.getTripsToPurchase(dateI,destino);
                    request.setAttribute("myListOfTrips", lista);
                } catch (ParseException e) {
                    out.print("Por favor insira uma data válida!");
                }
            }
        }
        request.getRequestDispatcher("/secured/purchaseTicket.jsp").include(request, response);
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<String> listaDestinos = ibusiness.getDestinos();
        if(listaDestinos!=null) {
            request.setAttribute("listDestinos", listaDestinos);
        }
        request.setAttribute("myListOfTrips", ibusiness.getTripsToPurchase(null,null));

        String tripid = request.getParameter("tripid");

        int userid = (int) request.getSession(true).getAttribute("auth");

        if (tripid != null ) {
            try {
                int id = parseInt(tripid);
                if (ibusiness.purchase(id,userid)){
                    out.println("Bilhete comprado!");
                }else{
                    out.println("Verifique o ID -> Viagem não existe ou já começou!");
                }
                request.getRequestDispatcher("/secured/purchaseTicket.jsp").include(request, response);
            }catch (NumberFormatException e){
                out.println("Por favor insira um ID válido!");
                request.getRequestDispatcher("/secured/purchaseTicket.jsp").include(request, response);
            } catch (TripFullException e) {
                out.println("A viagem selecionada já está lotada!");
                request.getRequestDispatcher("/secured/purchaseTicket.jsp").include(request, response);
            } catch (NotEnoughMoneyException e) {
                out.println("Sem dinheiro suficiente!");
                request.getRequestDispatcher("/secured/purchaseTicket.jsp").include(request, response);
            } catch (InvalidTripId invalidTripId) {
                out.println("Verifique o ID -> Viagem não existe ou já começou!");
                request.getRequestDispatcher("/secured/purchaseTicket.jsp").include(request, response);
            }
        }
        else{
            out.println("Por favor insira um ID!");
            request.getRequestDispatcher("/secured/purchaseTicket.jsp").include(request, response);
        }

        out.close();
    }

}