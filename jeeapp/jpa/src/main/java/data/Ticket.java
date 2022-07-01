package data;

import javax.persistence.*;
import java.security.PublicKey;
import java.util.List;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import java.io.Serializable;
import java.util.Date;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Trip trip;

    public Ticket() { }

    public Ticket(Passenger p, Trip t) {
        this.passenger=p;
        this.trip=t;
    }

    public Trip getTrip() {
        return trip;
    }

    public  Passenger getPassenger(){
        return passenger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}