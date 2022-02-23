package ro.fasttrackit.curs19homework.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String msg) {
        super(msg);
    }
}
