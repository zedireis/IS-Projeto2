package beans;

import data.CompanyManager;
import data.Passenger;
import data.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//import java.sql.Date;

@Singleton
@Startup
public class StartupBean {

    @PersistenceContext(unitName = "playAula")
    EntityManager em;

    Logger logger = LoggerFactory.getLogger(StartupBean.class);

    @PostConstruct
    public void initialize() {
        Passenger pa = new Passenger("user1", "user1@email.com", "user1");
        em.persist(pa);

        CompanyManager cm1 = new CompanyManager("zedireis", "zedireis@gmail.com", "zedireis");
        em.persist(cm1);

        CompanyManager cm2 = new CompanyManager("bruno", "brunogandres@gmail.com", "bruno");
        em.persist(cm2);

        Date t1, t2;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //Parsing the given String to Date object
        try {
            t1 = formatter.parse("2021-12-21 15:30");
            t2 = formatter.parse("2021-12-22 16:30");
            Trip t = new Trip(10, 50,"Coimbra", "Ancora", t1,t2);
            em.persist(t);

            t1 = formatter.parse("2021-12-23 15:30");
            t2 = formatter.parse("2021-12-24 16:30");
            t = new Trip(10, 50,"Figueira", "Coimbra", t1,t2);
            em.persist(t);

            t1 = formatter.parse("2021-12-14 12:00");
            t2 = formatter.parse("2021-12-16 10:30");
            t = new Trip(10, 50,"Coimbra", "Ancora", t1,t2);
            em.persist(t);

            t1 = formatter.parse("2021-12-16 15:30");
            t2 = formatter.parse("2021-12-18 16:30");
            t = new Trip(10, 50,"Coimbra", "Ancora", t1,t2);
            em.persist(t);
        }catch (ParseException p){
            System.out.printf("[STARTUP BEAN] -> ERRO NA DATA");
        }
    }

    @PreDestroy
    public void remove() {
        em.createQuery("DELETE FROM Ticket ").executeUpdate();
        em.createQuery("DELETE FROM Passenger p WHERE p.name LIKE 'user%'").executeUpdate();
        em.createQuery("DELETE FROM Trip ").executeUpdate();
        em.createQuery("DELETE FROM CompanyManager ").executeUpdate();
    }
}