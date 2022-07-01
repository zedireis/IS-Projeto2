package exceptions;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException(){
        super("Sem dinheiro suficiente");
    }
}
