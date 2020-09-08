package fr.ing.interview.controller;

import fr.ing.interview.dto.AccountDTO;
import fr.ing.interview.dto.TransactionInputDTO;
import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.service.AccountService;
import fr.ing.interview.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance/{customerId}/{accountId}")
    public BigDecimal getAccountBalance(@PathVariable("customerId") @NotNull String customerCode,
                                        @PathVariable("accountId") @NotNull String accountNumber) {
        return accountService.getAccountBalance(customerCode, accountNumber);
    }

    @PostMapping("/deposit")
    public AccountDTO depositAccount(@RequestBody @NotNull TransactionInputDTO transactionInputDTO) {
        Account account = accountService.transactionAccount(transactionInputDTO.getCustomerId(),
                TransactionTypeEnum.DEPOSIT, transactionInputDTO.getAccountId(), transactionInputDTO.getAmount());
        return new ModelMapper().map(account, AccountDTO.class);
    }

    @PostMapping("/withdraw")
    public AccountDTO withdrawAccount(@RequestBody @NotNull TransactionInputDTO transactionInputDTO) {
        Account account =  accountService.transactionAccount(transactionInputDTO.getCustomerId(),
                TransactionTypeEnum.WITHDRAM, transactionInputDTO.getAccountId(), transactionInputDTO.getAmount());
        return new ModelMapper().map(account, AccountDTO.class);
    }
}
