package servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.IBusiness;
import data.Trip;
import exceptions.InvalidTripId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet("/secured/refundticket")
public class RefundTicket extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Logger logger = LoggerFactory.getLogger(TripsAvailable.class);
    @EJB
    private IBusiness  ibusiness;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> myList;
        int userid = (int) request.getSession().getAttribute("auth");

        myList = ibusiness.getUnstartedTripBought(userid);

        request.setAttribute("myAllUnstartedTrips", myList);

        request.getRequestDispatcher("/secured/refundTicket.jsp").forward(request, response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        int userid = (int) request.getSession().getAttribute("auth");
        String ticketid = request.getParameter("ticketid");

        List<String> myList;



        try {
            ibusiness.returnTicket(Integer.parseInt(ticketid), userid);

            myList = ibusiness.getUnstartedTripBought(userid);
            request.setAttribute("myAllUnstartedTrips", myList);

            out.println("Bilhete reembolsado!");
            request.getRequestDispatcher("/secured/refundTicket.jsp").include(request, response);
        } catch (InvalidTripId invalidTripId) {
            myList = ibusiness.getUnstartedTripBought(userid);
            request.setAttribute("myAllUnstartedTrips", myList);

            out.println("Por favor insira um ID válido!");
            request.getRequestDispatcher("/secured/refundTicket.jsp").include(request, response);
        }

        out.close();
    }


}