package fr.ing.interview.service;

import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.Transaction;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.repository.TransactionRepository;
import fr.ing.interview.service.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTests {
    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void getAccountTransactionsHistoryNotFoundTest() {
        String customerName = "John Doe";
        String accountNumber = "0000";

        List<Transaction> results = transactionService.getAccountTransactionsHistory(customerName, accountNumber);
        Assert.assertNotNull(results);
    }

    @Test
    public void getAccountTransactionsHistoryOkTest() {
        String customerId = "000000001";
        String accountId = "0000000001";

        Account account = new Account();

        Transaction depositTransaction = new Transaction(TransactionTypeEnum.DEPOSIT, account, BigDecimal.TEN);
        Transaction withdrawTransaction = new Transaction(TransactionTypeEnum.WITHDRAM, account, BigDecimal.ONE);
        List<Transaction> transactions = Arrays.asList(depositTransaction, withdrawTransaction);

        Mockito.when(transactionRepository.findByAccountIdAndAccountCustomerIdOrderByTimeDesc(accountId, customerId))
            .thenReturn(transactions);

        List<Transaction> results = transactionService.getAccountTransactionsHistory(customerId, accountId);

        Assert.assertEquals(2, results.size());
    }

}
