package fr.ing.interview.controller;

import com.sun.istack.NotNull;
import fr.ing.interview.entity.Transaction;
import fr.ing.interview.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all/{customerCode}/{accountNumber}")
    public List<Transaction> getAccountBalance(@PathVariable("customerCode") @NotNull String customerCode,
                                               @PathVariable("accountNumber") @NotNull String accountNumber) {
        return transactionService.getAccountTransactionsHistory(customerCode, accountNumber);
    }
}
