package data;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripid;

    private int price, capacity;
    private String departure, destination;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio, dataFim;

    @OneToMany(mappedBy = "trip")
    private List<Ticket> tickets;

    public Trip() {
    }


    public Trip(int price, int capacity, String partida, String destino, Date dataPartida, Date dataChegada) {
        this.price=price;
        this.capacity=capacity;
        this.departure=partida;
        this.destination=destino;
        this.dataInicio=dataPartida;
        this.dataFim=dataChegada;
    }


    public List<Ticket> getTickets() {
        return tickets;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public int getTripid() {
        return tripid;
    }

    public void setTripid(int tripid) {
        this.tripid = tripid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return String.valueOf( this.tripid) + " " +this.departure + "->"  +this.destination
                + "   Partida: " + formatter.format(this.dataInicio);
    }

}