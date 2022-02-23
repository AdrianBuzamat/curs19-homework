package ro.fasttrackit.curs19homework.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs19homework.exception.TransactionNotFoundException;
import ro.fasttrackit.curs19homework.model.Transaction;
import ro.fasttrackit.curs19homework.model.TransactionType;
import ro.fasttrackit.curs19homework.service.TransactionsService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"transactions"})
public class BudgetController {
    private final TransactionsService service;

    public BudgetController(TransactionsService service) {
        this.service = service;
    }

     @GetMapping
    public List<Transaction> getAll(
             @RequestParam(required = false) String product,
             @RequestParam(required = false) TransactionType type,
             @RequestParam(defaultValue = "0") double minAmount,
             @RequestParam(defaultValue = "0") double maxAmount){
        return service.getAll(product, type, minAmount, maxAmount);
    }

    @GetMapping ("/{id}")
    public List<Transaction> getById(@PathVariable int id){
        return service.getTransactionsById(id);
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return service.add(transaction);
    }

    @PutMapping("{id}")
    public Transaction replaceWithID(@PathVariable int id, @RequestBody Transaction transaction){
        return service.replace(id, transaction)
                .orElseThrow(()-> new TransactionNotFoundException("Can not find transaction with ID" + id));
    }

    @DeleteMapping("{id}")
    public Transaction deleteWithID(@PathVariable int id){
        return service.delete(id)
                .orElse(null);
    }

    @GetMapping("/type")
    public Map<TransactionType, List<Transaction>> getByType(){
        return service.getMapByTransactionType();
    }

    @GetMapping("/product")
    public Map<String, List<Transaction>> getByProduct(){
        return service.getMapByTransactionProduct();
    }
}
