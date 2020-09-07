package fr.ing.interview.service;

import fr.ing.interview.KataApplicationTests;
import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.Transaction;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.exception.UnauthorizedOperationException;
import fr.ing.interview.repository.AccountRepository;
import fr.ing.interview.repository.TransactionRepository;
import fr.ing.interview.service.impl.AccountServiceImpl;
import fr.ing.interview.service.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TransactionServiceImplTest extends KataApplicationTests {
    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void getAccountTransactionsHistoryNotFoundTest() {
        String customerName = "John Doe";
        String accountNumber = "0000";

        List<Transaction> results = transactionService.getAccountTransactionsHistory(customerName, accountNumber);
        Assert.assertNull(results);
    }

    @Test
    public void getAccountTransactionsHistoryOkTest() {
        String customerCode = "1";
        String accountNumber = "0000";

        Account account = new Account();

        Transaction depositTransaction = new Transaction(TransactionTypeEnum.DEPOSIT, account, BigDecimal.TEN);
        Transaction withdrawTransaction = new Transaction(TransactionTypeEnum.WITHDRAM, account, BigDecimal.ONE);
        List<Transaction> transactions = Arrays.asList(depositTransaction, withdrawTransaction);

        Mockito.when(transactionRepository.findByAccountNumberAndAccountCustomerCodeOrderByTimeDesc(accountNumber, customerCode))
            .thenReturn(transactions);

        List<Transaction> results = transactionService.getAccountTransactionsHistory(customerCode, accountNumber);

        Assert.assertEquals(2, results.size());
    }

}
