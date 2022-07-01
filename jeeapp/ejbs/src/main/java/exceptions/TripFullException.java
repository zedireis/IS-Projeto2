package exceptions;

public class TripFullException extends Exception {
    public TripFullException(){
        super("A viagem para a qual deseja comprar bilhete já está lotada");
    }
}
