package fr.ing.interview.service;

import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.Transaction;
import fr.ing.interview.entity.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAccountTransactionsHistory(String customerName, String accountNumber);

    void saveTransaction(TransactionTypeEnum transactionTypeEnum, Account account, BigDecimal amount);
}
