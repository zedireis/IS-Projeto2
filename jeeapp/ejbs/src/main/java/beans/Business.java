package beans;

import data.*;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.*;
import java.util.*;

import exceptions.InvalidTripId;
import exceptions.NotEnoughMoneyException;
import exceptions.TripFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static data.PassBasedEnc.generateSecurePassword;


@Stateless
@Remote( IBusiness.class )
public class Business implements IBusiness{
    Logger logger = LoggerFactory.getLogger(Business.class);


    @PersistenceContext(unitName = "playAula")
    EntityManager em;

    @Resource(mappedName="java:jboss/mail/Default")
    private Session mailSession;

    public boolean isManager(int id){//NEW
        Passenger p = em.find(Passenger.class, id);
        return p.isManager();
    }

    public boolean addUser(String email, String name, String password){
        Long existe = (Long) em.createQuery("SELECT count(p) FROM Passenger p WHERE email =: mail").setParameter("mail",email).getSingleResult();
        if(!existe.equals(0L)){
            return false;
        }

        Passenger pa = new Passenger(name, email, password);
        em.persist(pa);
        return true;
    }

    public void addManager(String email, String name, String password){
        CompanyManager m = new CompanyManager(name, email, password);
        em.persist(m);
    }

    /*requirement 4*/
    public boolean loginValidation(String email, String password){
        System.out.println("EMAIL: "+email);
        System.out.println("PASSWORD: "+password);

        TypedQuery<Object[]> q = em.createQuery("select p.password, p.salt from Passenger p where p.email=: email", Object[].class);
        q.setParameter("email", email);
        List<Object[]> lp = q.getResultList();

        if(lp.isEmpty()) {
            return false;
        }
        else{
            boolean finalval=false;
            String passwordDatabase = (String) lp.get(0)[0];
            String salt = (String) lp.get(0)[1];

            String newPassword = generateSecurePassword(password, salt);

            finalval = newPassword.equalsIgnoreCase(passwordDatabase);

            logger.debug("Password database:"+passwordDatabase);
            logger.debug("Password given:"+password);

            logger.debug("Password igual?: "+ finalval);

            return finalval;
        }

    }


    public int getUserId(String email){
        TypedQuery<Passenger> q = em.createQuery("from Passenger p where p.email=: email", Passenger.class);
        q.setParameter("email", email);
        List<Passenger> lp = q.getResultList();
        return lp.get(0).getId();
    }
    /* requirement 6*/
    public boolean editUser(int id, String email, String name, String password){
        Passenger p = em.find(Passenger.class, id);

        if(email != null){
            Long existe = (Long) em.createQuery("SELECT count(p) FROM Passenger p WHERE email =: mail").setParameter("mail",email).getSingleResult();
            if(!existe.equals(0L)){
                return false;
            }else {
                p.setEmail(email);
            }
        }
        if(name != null){
            p.setName(name);
        }
        if(password != null){
            p.setPassword(password);
        }
        return true;
    }


    /* requirement 7*/
    public void deleteUser(int id){
        //Ver se depois os bilhetes também são apagados

        Passenger p = em.find(Passenger.class, id);
        em.createQuery("DELETE FROM Ticket t WHERE t.passenger =: id").setParameter("id",p).executeUpdate();
        em.remove(p);

    }

    /* requirement 8*/
    public List<String> tripsAvailables(Date dataInicio, Date dataFim){
        Date now = new Date();
        if(dataInicio.before(now)){
            dataInicio=now;
        }
        TypedQuery<Trip> q = em.createQuery("select t from Trip t where t.dataInicio >=: dataInicio and t.dataFim <=: dataFim", Trip.class);
        q.setParameter("dataInicio", dataInicio);
        q.setParameter("dataFim", dataFim);
        List<Trip> lp = q.getResultList();

        if(lp.size() != 0){
            List<String> myList = new ArrayList<String>();

            String s = "";
            for(int i = 0; i < lp.size();i++){

                s = lp.get(i).toString();
                myList.add(s);
            }
            //logger.info("[BUSINESS] Lista: " + '\n' + s);
            return myList;
        }
        else{
            List<String> myList = new ArrayList<String>();
            String s = "Sem trips";
            myList.add(s);
            return  myList;
        }
    }

    public int getSaldo(int id){
        Passenger u = em.find(Passenger.class, id);
        return u.getDinheiro();
    }

    /*requirement 9*/
    public void CarregaCarteira(int id, int valor){
        Passenger u = em.find(Passenger.class, id);
        u.addDinheiro(valor);
    }

    public List<Passenger> getTripPassengers(int tripid){
        Trip t = em.find(Trip.class, tripid);
        TypedQuery<Passenger> q = em.createQuery("SELECT t.passenger FROM Ticket t WHERE t.trip =: tripid", Passenger.class);
        q.setParameter("tripid", t);
        return q.getResultList();
    }

    public List<String> getTripsToPurchase(Date dataInicio, String destino) {
        List<Trip> lp;
        Date now = new Date();
        if(dataInicio==null){
            dataInicio = now;
        }else if(dataInicio.before(now)){
            dataInicio = now;
        }

        TypedQuery<Trip> q;
        if(destino==null){
            q = em.createQuery("select t from Trip t where t.dataInicio >=: dataInicio", Trip.class);
            q.setParameter("dataInicio", dataInicio);
        }else{
            q = em.createQuery("select t from Trip t where t.dataInicio >=: dataInicio and t.destination =: destino", Trip.class);
            q.setParameter("dataInicio", dataInicio);
            q.setParameter("destino", destino);
        }
        lp = q.getResultList();

        if(lp.size() != 0){
            List<String> myList = new ArrayList<String>();

            //logger.info("[BUSINESS] Tamanho Lista: "  + lp.size());
            String s = "";
            for(int i = 0; i < lp.size();i++){

                s = lp.get(i).toString();
                myList.add(s);
            }
            //logger.info("[BUSINESS] Lista: " + '\n' + s);
            return myList;
        }
        else{
            List<String> myList = new ArrayList<String>();
            String s = "Sem trips";
            myList.add(s);
            return  myList;
        }
    }

    public List<String> getDestinos(){

        TypedQuery<String> q = em.createQuery("select distinct t.destination from Trip t where t.dataInicio >=: dataInicio", String.class);
        q.setParameter("dataInicio", new Date());

        List<String> lp = q.getResultList();

        if(lp.size() != 0){
            return lp;
        }else{
            return null;
        }
    }

    /*requirement 10 */
    public boolean purchase(int tripid, int userid) throws TripFullException, NotEnoughMoneyException, InvalidTripId {
        Passenger u = em.find(Passenger.class, userid);
        Trip t = em.find(Trip.class, tripid);
        if(t==null || t.getDataInicio().before(new Date())){
            throw new InvalidTripId();
        }
        if(getTripPassengers(tripid).size() == t.getCapacity()){
            //return false;
            throw new TripFullException();
        }
        if(u.getDinheiro() < t.getPrice()){
            //return  false;
            throw new NotEnoughMoneyException();
        }
        //TripData td = new TripData(place, t,u);
        //em.persist(td);
        Ticket tick = new Ticket(u,t);
        em.persist(tick);
        u.addDinheiro(-t.getPrice());

        return true;
    }


    public List<String> getUnstartedTripBought(int userid) {
        Passenger u = em.find(Passenger.class, userid);

        //current date
        Date date = new Date();

        TypedQuery<Ticket> q = em.createQuery("SELECT t FROM Ticket t WHERE t.passenger =: user AND t.trip.dataInicio >: data", Ticket.class);
        q.setParameter("user", u);
        q.setParameter("data", date);
        List<Ticket> lp = q.getResultList();
        List<String> myList = new ArrayList<String>();

        if(lp.size() != 0){
            Ticket t;
            //logger.info("[BUSINESS] Tamanho Lista: "  + lp.size());
            for(int i = 0; i < lp.size();i++){
                t = lp.get(i);
                String s = "Ticket: " + Integer.toString(t.getId()) + " [" + t.getTrip().toString() + "]";
                myList.add(s);
            }
        }

        return myList;

    }
    /*requirement 11 */
    public void returnTicket(int ticketid, int userid) throws InvalidTripId {
        Passenger u = em.find(Passenger.class, userid);

        Ticket t = em.find(Ticket.class, ticketid);
        if(t == null){
            throw new InvalidTripId();
        }else{
            u.addDinheiro(t.getTrip().getPrice());
            em.remove(t);
        }
    }

    /*Requirement 12*/
    public List<String> getTripHistory(int userid){
        Passenger u = em.find(Passenger.class, userid);
        TypedQuery<Trip> q = em.createQuery("SELECT distinct t.trip FROM Ticket t WHERE t.passenger =: user", Trip.class);
        q.setParameter("user", u);
        List<Trip> lp = q.getResultList();
        List<String> myList = new ArrayList<String>();
        if(lp.size() != 0){

            //logger.info("[BUSINESS] Tamanho Lista: "  + lp.size());
            String s = "";
            for(int i = 0; i < lp.size();i++){

                s = lp.get(i).toString();
                myList.add(s);
            }
            //logger.info("[BUSINESS] Lista: " + '\n' + s);
        }
        else{
            String s = "Sem trips";
            myList.add(s);
        }
        return myList;
    }

    /*requirement 13*/

    public void addTrip(String partida, String destino, String capacidade, String preco, Date dataPartida, Date dataChegada){
        Trip t = new Trip(Integer.parseInt(preco), Integer.parseInt(capacidade),
                partida, destino, dataPartida, dataChegada);
        em.persist(t);
    }

    public List<String> getUnstartedTrips() {
        //current date
        Date date = new Date();

        TypedQuery<Trip> q = em.createQuery("SELECT t FROM Trip t WHERE t.dataInicio >: data", Trip.class);
        q.setParameter("data", date);
        List<Trip> lp = q.getResultList();
        List<String> myList = new ArrayList<String>();

        if(lp.size() != 0){
            for(int i = 0; i < lp.size(); i++){
                String s = lp.get(i).toString();
                myList.add(s);
            }
        }

        return myList;

    }

    /*Requirement 14*/
    public boolean deleteTrip(int tripid){
        Trip t = em.find(Trip.class, tripid);

        if(t == null) {
            return false;
        }

        TypedQuery<Object[]> q = em.createQuery("SELECT t.passenger, t from Ticket t where t.trip.id=: tripid", Object[].class);
        q.setParameter("tripid", tripid);
        List<Object[]> lp = q.getResultList();

        logger.info("A eliminar viagem: "+t.toString()+" com "+lp.size()+" bilhetes");

       if(t.getDataInicio().before(new Date())){
            return false;
        }else if(lp.size()==0){
            em.remove(t);
            return true;
        }

        int preco = t.getPrice();
        Passenger p;
        Ticket tick;
        String mail =",\nInformamos que a sua viagem: "+t.toString()+" foi cancelada e o dinheiro do bilhete reposto na sua conta.\nObrigado";

        for(Object[] o: lp){
            p = (Passenger) o[0];
            p.addDinheiro(preco);

            logger.info("Refunding ->"+p.getName());

            //ENVIAR EMAIL
            try    {
                MimeMessage m = new MimeMessage(mailSession);
                Address from = new InternetAddress("zedireis@gmail.com");
                Address[] to = new InternetAddress[] {new InternetAddress(p.getEmail()) };
                m.setFrom(from);
                m.setRecipients(Message.RecipientType.TO, to);
                m.setSubject("Cancelamento de viagem");
                m.setSentDate(new java.util.Date());

                m.setContent("Caro "+p.getName()+mail,"text/plain");
                Transport.send(m);
                logger.info("Email enviado ->"+p.getEmail());
            }
            catch (javax.mail.MessagingException e)
            {
                logger.error(e.toString());
            }

            tick = (Ticket) o[1];
            em.remove(tick);
        }

        em.remove(t);
        return true;
    }

    /*Requirement 15*/
    public List<String> getTopFive(){
        Query q = em.createQuery("SELECT t.passenger.id FROM Ticket t GROUP BY t.passenger.id ORDER BY count(t.id) DESC ");
        List lp = q.getResultList();
        List<String> myList = new ArrayList<String>();
        if(lp.size() != 0){
            String s = "";
            Passenger p;
            for(int i = 0; i < lp.size() && i < 5 ;i++){
                p = em.find(Passenger.class, (int) lp.get(i));
                s = "#"+(i+1)+"->"+p.getName();
                myList.add(s);
            }
        }
        else{
            String s = "Sem trips ou bilhetes comprados";
            myList.add(s);
        }
        return myList;
    }

    /*Requirement 16*/
    public List<String> tripsAvailablesSorted(Date dataInicio, Date dataFim){

        logger.info("Data de Inicio: " + dataInicio);
        TypedQuery<Trip> q = em.createQuery("select t from Trip t where t.dataInicio >=: dataInicio and t.dataFim <=: dataFim ORDER BY t.dataInicio", Trip.class);
        q.setParameter("dataInicio", dataInicio);
        q.setParameter("dataFim", dataFim);
        List<Trip> lp = q.getResultList();

        if(lp.size() != 0){
            List<String> myList = new ArrayList<String>();

            String s = "";
            for(int i = 0; i < lp.size();i++){

                s = lp.get(i).toString();
                myList.add(s);
            }
            //logger.info("[BUSINESS] Lista: " + '\n' + s);
            return myList;
        }
        else{
            List<String> myList = new ArrayList<String>();
            String s = "Sem trips";
            myList.add(s);
            return  myList;
        }
    }

    /*Requirement 17*/
    public Map<Integer, String> getTripsByDate(Date dataInicio, Date dataFim) {
        TypedQuery<Trip> q;
        List<Trip> lp;
        if(dataFim==null) {
            q = em.createQuery("select t from Trip t where date(t.dataInicio) =: dataInicio", Trip.class);
            q.setParameter("dataInicio", dataInicio);
        }else {
            q = em.createQuery("select t from Trip t where t.dataInicio >=: dataInicio and t.dataFim <=: dataFim", Trip.class);
            q.setParameter("dataInicio", dataInicio);
            q.setParameter("dataFim", dataFim);
        }
        lp = q.getResultList();

        Map<Integer, String> hm1 = new HashMap<>();
        if(lp.size() != 0){
            for(int i = 0; i < lp.size();i++){
                hm1.put(lp.get(i).getTripid(), lp.get(i).toString());
            }
        }
        else{
            List<String> myList = new ArrayList<String>();
            hm1.put(-1,"Sem trips");
        }
        return hm1;

    }

    /*Requirement 18*/
    public List<String> getTicketsForTrip(int tripid){
        Trip t = em.find(Trip.class, tripid);
        TypedQuery<Passenger> q = em.createQuery("SELECT distinct t.passenger FROM Ticket t WHERE t.trip =: tripid", Passenger.class);
        q.setParameter("tripid", t);
        List<Passenger> lista = q.getResultList();

        List<String> lp = new ArrayList<String>();
        if(!lista.isEmpty()){
            for (Passenger p:lista) {
                lp.add("Nome: "+p.getName()+" Email: "+p.getEmail());
            }
        }else{
            lp.add("Sem passageiros");
        }
        return lp;
    }

    /*Requirement 19*/
    //(hour = "14", minute = "30")
    @Schedule(hour = "23", minute = "59")
    public void sendDailySummary() {
        Date now = new Date();
        TypedQuery<Trip> q = em.createQuery("select t from Trip t where date(t.dataInicio) =: dataInicio", Trip.class);
        q.setParameter("dataInicio", now);
        List<Trip> lp = q.getResultList();

        String mail = "Resumo das receitas das viagens de hoje ->"+now.toString();
        int revenue;
        int totalRevenue=0;
        for (Trip t: lp) {
            revenue = t.getTickets().size() * t.getPrice();
            totalRevenue+=revenue;
            mail = String.join("\n",mail,t.toString()+" Receitas->"+revenue+" EUROS");
        }
        mail = String.join("\n",mail,"\nTotal------>"+totalRevenue+" EUROS");

        TypedQuery<String> q1 = em.createQuery("select m.email from CompanyManager m", String.class);
        List<String> mails = q1.getResultList();
        for(String email: mails) {
            //ENVIAR EMAIL
            try {
                MimeMessage m = new MimeMessage(mailSession);
                Address from = new InternetAddress("zedireis@gmail.com");
                Address[] to = new InternetAddress[]{new InternetAddress(email)};
                m.setFrom(from);
                m.setRecipients(Message.RecipientType.TO, to);
                m.setSubject("Sumario do dia");
                m.setSentDate(new java.util.Date());

                m.setContent(mail, "text/plain");
                Transport.send(m);
                logger.info("Email de sumario enviado ->" + email);
            } catch (javax.mail.MessagingException e) {
                logger.error(e.toString());
            }
        }
    }

    /*public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projeto");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        System.out.println("olá a todos\n");
        *//*
        for (Student s : mystudents)
            em.persist(s);*//*
        et.commit();
    }*/



}