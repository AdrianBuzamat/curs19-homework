package ro.fasttrackit.curs19homework.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.curs19homework.model.Transaction;
import ro.fasttrackit.curs19homework.model.TransactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionsService {
    private final List<Transaction> transactions;

    public TransactionsService(TransactionsProvider transactionsProvider) {
        this.transactions = new ArrayList<>(transactionsProvider.getTransactions());
    }

    public List<Transaction> getAll(String product, TransactionType type, double minAmount, double maxAmount) {
        return transactions.stream()
                .filter(p -> product == null || p.product().equalsIgnoreCase(product))
                .filter(p -> type == null || p.type().equals(type))
                .filter(p -> minAmount == 0 || p.amount() > minAmount)
                .filter(p -> maxAmount == 0 || p.amount() < maxAmount)
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsById(int id) {
        return transactions.stream()
                .filter(p -> p.id() == id)
                .collect(Collectors.toList());
    }

    public Transaction add(Transaction transaction) {
        Transaction newTransaction = cloneWithId(transactions.size(), transaction);
        this.transactions.add(newTransaction);
        return newTransaction;
    }

    private Transaction cloneWithId(int id, Transaction transaction) {
        return new Transaction(
                id,
                transaction.product(),
                transaction.type(),
                transaction.amount()
        );
    }

    public Optional<Transaction> replace(int id, Transaction transaction) {
        return findById(id)
                .map(existing -> replaceExistingTransaction(id, existing, transaction));
    }

    public Optional<Transaction> delete(int id) {
        Optional<Transaction> transactionToDelete = findById(id);
        transactionToDelete.ifPresent(transactions::remove);
        return transactionToDelete;
    }

    public Map<TransactionType, List<Transaction>> getMapByTransactionType(){
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type));
    }

    public Map<String, List<Transaction>> getMapByTransactionProduct() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::product));
    }

    private Transaction replaceExistingTransaction(int id, Transaction existing, Transaction transaction) {
        this.transactions.remove(existing);
        Transaction newTransaction = cloneWithId(id, transaction);
        this.transactions.add(id - 1, newTransaction);
        return newTransaction;
    }

    public Optional<Transaction> findById(int id) {
        return this.transactions.stream()
                .filter(transaction -> transaction.id() == id)
                .findFirst();
    }


}
