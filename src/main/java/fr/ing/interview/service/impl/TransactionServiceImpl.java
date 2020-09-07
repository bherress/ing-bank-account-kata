package fr.ing.interview.service.impl;

import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.Transaction;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.repository.TransactionRepository;
import fr.ing.interview.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAccountTransactionsHistory(String customerCode, String accountNumber) {
        return transactionRepository.findByAccountNumberAndAccountCustomerCodeOrderByTimeDesc(accountNumber, customerCode);
    }

    @Override
    public void saveTransaction(TransactionTypeEnum transactionTypeEnum, Account account, BigDecimal amount) {
        Transaction transaction = new Transaction(transactionTypeEnum, account, amount);
        transactionRepository.save(transaction);
    }
}
