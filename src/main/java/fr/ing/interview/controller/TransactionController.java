package fr.ing.interview.controller;

import fr.ing.interview.dto.TransactionOutputDTO;
import fr.ing.interview.entity.Transaction;
import fr.ing.interview.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all/{customerId}/{accountId}")
    public List<TransactionOutputDTO> getAccountTransactions(@PathVariable("customerId") @NotNull String customerId,
                                                        @PathVariable("accountId") @NotNull String accountId) {
        List<Transaction> transactions = transactionService.getAccountTransactionsHistory(customerId, accountId);
        return transactions.stream()
                .map(transaction -> new ModelMapper().map(transaction, TransactionOutputDTO.class))
                .collect(Collectors.toList());
    }
}
