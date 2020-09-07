package fr.ing.interview.service;

import fr.ing.interview.KataApplicationTests;
import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.exception.UnauthorizedOperationException;
import fr.ing.interview.repository.AccountRepository;
import fr.ing.interview.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class AccountServiceImplTest extends KataApplicationTests {
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
        String customerCode = "1";
        String accountNumber = "0000";
        BigDecimal amount = BigDecimal.ZERO;

        Account account = new Account();

        Mockito.when(accountRepository.findByNumberAndCustomerCode(accountNumber, customerCode))
                .thenReturn(account);

        accountService.transactionAccount(customerCode, TransactionTypeEnum.DEPOSIT, accountNumber, amount);
    }

    @Test
    public void transactionAccountDepositOkTest() {
        String customerCode = "1";
        String accountNumber = "0000";
        BigDecimal amount = BigDecimal.TEN;

        Account account = new Account();
        account.setBalance(BigDecimal.ONE);

        Mockito.when(accountRepository.findByNumberAndCustomerCode(accountNumber, customerCode))
                .thenReturn(account);
        Mockito.doNothing().when(transactionService).saveTransaction(TransactionTypeEnum.DEPOSIT, account, amount);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.transactionAccount(customerCode, TransactionTypeEnum.DEPOSIT, accountNumber, amount);
        Assert.assertEquals(BigDecimal.TEN.add(BigDecimal.ONE), result.getBalance());
    }

    @Test(expected = UnauthorizedOperationException.class)
    public void transactionAccountWithdrawNegativeBalanceTest() {
        String customerCode = "1";
        String accountNumber = "0000";
        BigDecimal amount = BigDecimal.TEN;

        Account account = new Account();
        account.setBalance(BigDecimal.ONE);

        Mockito.when(accountRepository.findByNumberAndCustomerCode(accountNumber, customerCode))
                .thenReturn(account);

        accountService.transactionAccount(customerCode, TransactionTypeEnum.WITHDRAM, accountNumber, amount);
    }

    @Test
    public void transactionAccountWithdrawOkTest() {
        String customerCode = "1";
        String accountNumber = "0000";
        BigDecimal amount = BigDecimal.ONE;

        Account account = new Account();
        account.setBalance(BigDecimal.TEN);

        Mockito.when(accountRepository.findByNumberAndCustomerCode(accountNumber, customerCode))
                .thenReturn(account);
        Mockito.doNothing().when(transactionService).saveTransaction(TransactionTypeEnum.WITHDRAM, account, amount);
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.transactionAccount(customerCode, TransactionTypeEnum.WITHDRAM, accountNumber, amount);
        Assert.assertEquals(BigDecimal.TEN.subtract(BigDecimal.ONE), result.getBalance());
    }

    @Test(expected = UnauthorizedOperationException.class)
    public void getAccountBalanceNotFoundTest(){
        String customerCode = "1";
        String accountNumber = "0000";

        accountService.getAccountBalance(customerCode, accountNumber);
    }

    @Test
    public void getAccountBalanceOkTest(){
        String customerCode = "1";
        String accountNumber = "0000";

        Account account = new Account();
        account.setBalance(BigDecimal.TEN);

        Mockito.when(accountRepository.findByNumberAndCustomerCode(accountNumber, customerCode))
                .thenReturn(account);

        BigDecimal balance =accountService.getAccountBalance(customerCode, accountNumber);
        Assert.assertEquals(account.getBalance(), balance);
    }
}
