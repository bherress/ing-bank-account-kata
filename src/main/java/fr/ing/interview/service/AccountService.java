package fr.ing.interview.service;

import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.TransactionTypeEnum;

import java.math.BigDecimal;

public interface AccountService {

    Account transactionAccount(String customerName, TransactionTypeEnum transactionTypeEnum,
                               String accountNumber, BigDecimal amount);

    BigDecimal getAccountBalance(String customerName, String accountNumber);
}
