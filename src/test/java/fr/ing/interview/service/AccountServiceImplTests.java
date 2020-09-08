package fr.ing.interview.service;

import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.exception.UnauthorizedOperationException;
import fr.ing.interview.repository.AccountRepository;
import fr.ing.interview.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTests {
    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionService transactionService;

    @Test(expected = UnauthorizedOperationException.class)
    public void transactionAccountNotFoundTest() {
        String customerCode = "1";
        String accountNumber = "0000";
        BigDecimal amount = BigDecimal.TEN;

        accountService.transactionAccount(customerCode, TransactionTypeEnum.DEPOSIT, accountNumber, amount);
    }

    @Test(expected = UnauthorizedOperationException.class)
    public void transactionAccountDepositNotValidAmountTest() {
        String customerId = "1000111001";
        String accountId = "0000001010";
        BigDecimal amount = BigDecimal.ZERO;

        Account account = new Account();

        Mockito.when(accountRepository.findByIdAndCustomerId(accountId, customerId))
                .thenReturn(account);

        accountService.transactionAccount(customerId, TransactionTypeEnum.DEPOSIT, accountId, amount);
    }

    @Test
    public void transactionAccountDepositOkTest() {
        String customerId = "1000111001";
        String accountId = "0000001010";
        BigDecimal amount = BigDecimal.TEN;

        Account account = new Account();
        account.setBalance(BigDecimal.ONE);

        Mockito.when(accountRepository.findByIdAndCustomerId(accountId, customerId))
                .thenReturn(account);
        Mockito.doNothing().when(transactionService).saveTransaction(TransactionTypeEnum.DEPOSIT, account, amount);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.transactionAccount(customerId, TransactionTypeEnum.DEPOSIT, accountId, amount);
        Assert.assertEquals(BigDecimal.TEN.add(BigDecimal.ONE), result.getBalance());
    }

    @Test(expected = UnauthorizedOperationException.class)
    public void transactionAccountWithdrawNegativeBalanceTest() {
        String customerId = "1000111001";
        String accountId = "0000001010";
        BigDecimal amount = BigDecimal.TEN;

        Account account = new Account();
        account.setBalance(BigDecimal.ONE);

        Mockito.when(accountRepository.findByIdAndCustomerId(accountId, customerId))
                .thenReturn(account);

        accountService.transactionAccount(customerId, TransactionTypeEnum.WITHDRAM, accountId, amount);
    }

    @Test
    public void transactionAccountWithdrawOkTest() {
        String customerId = "1000111001";
        String accountId = "0000001010";
        BigDecimal amount = BigDecimal.ONE;

        Account account = new Account();
        account.setBalance(BigDecimal.TEN);

        Mockito.when(accountRepository.findByIdAndCustomerId(accountId, customerId))
                .thenReturn(account);
        Mockito.doNothing().when(transactionService).saveTransaction(TransactionTypeEnum.WITHDRAM, account, amount);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.transactionAccount(customerId, TransactionTypeEnum.WITHDRAM, accountId, amount);
        Assert.assertEquals(BigDecimal.TEN.subtract(BigDecimal.ONE), result.getBalance());
    }

    @Test(expected = UnauthorizedOperationException.class)
    public void getAccountBalanceNotFoundTest(){
        String customerId = "1000111001";
        String accountId = "0000001010";

        accountService.getAccountBalance(customerId, accountId);
    }

    @Test
    public void getAccountBalanceOkTest(){
        String customerId = "1000111001";
        String accountId = "0000001010";

        Account account = new Account();
        account.setBalance(BigDecimal.TEN);

        Mockito.when(accountRepository.findByIdAndCustomerId(accountId, customerId))
                .thenReturn(account);

        BigDecimal balance =accountService.getAccountBalance(customerId, accountId);
        Assert.assertEquals(account.getBalance(), balance);
    }
}
