package ro.fasttrackit.curs19homework.model;

public record Transaction(int id, String product, TransactionType type, double amount) {
}
