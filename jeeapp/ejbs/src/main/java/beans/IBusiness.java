package beans;

import exceptions.InvalidTripId;
import exceptions.NotEnoughMoneyException;
import exceptions.TripFullException;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Remote
public interface IBusiness {
    public boolean addUser(String email, String name, String password);
    public int getUserId(String email);
    public boolean loginValidation(String email, String password);
    public boolean editUser(int id, String email, String name, String password);
    public void deleteUser(int id);
    public void CarregaCarteira(int id, int valor);
    public List<String> tripsAvailables(Date dataInicio, Date dataFim);
    public boolean isManager(int id);//NEW
    public boolean deleteTrip(int tripid);//NEW
    public void addTrip(String partida, String destino, String capacidade, String preco, Date dataPartida, Date dataChegada);
    public List<String> getTripsToPurchase(Date dataInicio, String destino);
    public List<String> getDestinos();
    public boolean purchase(int tripid, int userid) throws TripFullException, NotEnoughMoneyException, InvalidTripId;
    public int getSaldo(int id);
    public List<String> getTripHistory(int userid);
    public List<String> getTopFive();
    public List<String> getUnstartedTripBought(int userid);
    public void returnTicket(int ticketid, int userid) throws InvalidTripId;
    public List<String> getUnstartedTrips();
    public List<String> tripsAvailablesSorted(Date dataInicio, Date dataFim);
    public Map<Integer, String> getTripsByDate(Date dataInicio, Date dataFim);
    public List<String> getTicketsForTrip(int tripid);
}