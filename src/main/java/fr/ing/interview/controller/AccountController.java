package fr.ing.interview.controller;

import com.sun.istack.NotNull;
import fr.ing.interview.bean.TransactionDTO;
import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/balance/{customerCode}/{accountNumber}")
    public BigDecimal getAccountBalance(@PathVariable("customerCode") @NotNull String customerCode,
                                        @PathVariable("accountNumber") @NotNull String accountNumber) {

        return accountService.getAccountBalance(customerCode, accountNumber);
    }

    @PostMapping("/deposit")
    public Account depositAccount(@RequestBody @NotNull TransactionDTO transactionDTO) {
        return accountService.transactionAccount(transactionDTO.getCustomerCode(),
                TransactionTypeEnum.DEPOSIT, transactionDTO.getAccountNumber(), transactionDTO.getAmount());
    }

    @PostMapping("/withdraw")
    public Account withdrawAccount(@RequestBody @NotNull TransactionDTO transactionDTO) {
        return accountService.transactionAccount(transactionDTO.getCustomerCode(),
                TransactionTypeEnum.WITHDRAM, transactionDTO.getAccountNumber(), transactionDTO.getAmount());
    }
}
