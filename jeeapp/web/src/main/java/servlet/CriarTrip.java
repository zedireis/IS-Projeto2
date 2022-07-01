package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

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


@WebServlet("/secured/criartrip")
public class CriarTrip extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private IBusiness  ibusiness;

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String partida = request.getParameter("partida");
        String destino = request.getParameter("chegada");
        String capacidade = request.getParameter("capacidade");
        String preco = request.getParameter("preco");
        String dateInicio = request.getParameter("dateInicio");
        String dateFim = request.getParameter("dateFim");
        String horaPartida = request.getParameter("horaPartida");
        String horaChegada = request.getParameter("horaChegada");


        //[PROTEÇÃO DE DADOS]se puser um espaço em cada input corre na mesma.

        if(partida != null && destino != null && capacidade != null && preco != null
            && dateInicio != null && dateFim != null){

            String dataPartida =  dateInicio+ " " + horaPartida + ":00";
            String dataChegada =  dateFim+ " " + horaChegada + ":00";
            SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dateP=formatter6.parse(dataPartida);
                Date dateC=formatter6.parse(dataChegada);

                ibusiness.addTrip(partida,destino,capacidade,preco,dateP,dateC);

                request.getRequestDispatcher("mainPage.jsp").forward(request, response);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else{
            out.println("[Criar Trip] Informacoes imcompletas");
            request.getRequestDispatcher("criarTrip.jsp").include(request, response);
        }
        out.close();
    }

}