package exceptions;

public class InvalidTripId extends Exception {
    public InvalidTripId(){
        super("A viagem já começou ou não existe");
    }
}
