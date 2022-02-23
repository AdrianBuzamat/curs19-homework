package ro.fasttrackit.curs19homework.service;

import org.springframework.stereotype.Component;
import ro.fasttrackit.curs19homework.model.Transaction;

import java.util.List;

import static ro.fasttrackit.curs19homework.model.TransactionType.BUY;
import static ro.fasttrackit.curs19homework.model.TransactionType.SELL;

@Component
public class TransactionsProvider {
    public List<Transaction> getTransactions(){
        return List.of(
                new Transaction(1, "AAPL", BUY, 2.4),
                new Transaction(2, "TSLA", BUY, 3.4),
                new Transaction(3, "PATH", SELL, 55.4),
                new Transaction(4, "SPCE", BUY, 23.3),
                new Transaction(5, "PLTR", SELL, 33.7),
                new Transaction(6, "PLTR", BUY, 13.7)
        );
    }
}
