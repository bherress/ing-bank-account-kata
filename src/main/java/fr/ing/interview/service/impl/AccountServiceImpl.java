package fr.ing.interview.service.impl;

import fr.ing.interview.entity.Account;
import fr.ing.interview.entity.TransactionTypeEnum;
import fr.ing.interview.exception.ErrorTypeEnum;
import fr.ing.interview.exception.UnauthorizedOperationException;
import fr.ing.interview.repository.AccountRepository;
import fr.ing.interview.service.AccountService;
import fr.ing.interview.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    private static final BigDecimal MINIMAL_DEPOSIT_AMOUT = new BigDecimal("0.01");

    @Override
    @Transactional
    public Account transactionAccount(String customerId, TransactionTypeEnum transactionTypeEnum, String accountId, BigDecimal amount){
        Account account = getAccountByCustomerIdAndAccountId(customerId, accountId);
        return TransactionTypeEnum.DEPOSIT.equals(transactionTypeEnum) ?
                depositAccount(account, amount) : withDrawAccount(account, amount);
    }

    @Override
    public BigDecimal getAccountBalance(String customerId, String accountId){
        return getAccountByCustomerIdAndAccountId(customerId, accountId).getBalance();
    }

    private Account getAccountByCustomerIdAndAccountId(String customerId, String accountId) {
        Account account = accountRepository.findByIdAndCustomerId(accountId, customerId);
        if(account == null){
            throw new UnauthorizedOperationException(ErrorTypeEnum.ACCOUNT_CUSTOMER_NOT_FOUND);
        }

        return account;
    }

    private Account depositAccount(Account account, BigDecimal amount){
        if(MINIMAL_DEPOSIT_AMOUT.compareTo(amount) >= 0) {
            throw new UnauthorizedOperationException(ErrorTypeEnum.NOT_VALID_AMOUNT);
        }

        account.setBalance(account.getBalance().add(amount));
        transactionService.saveTransaction(TransactionTypeEnum.DEPOSIT, account, amount);
        return accountRepository.save(account);
    }

    private Account withDrawAccount(Account account, BigDecimal amount){
        BigDecimal newBalance = account.getBalance().subtract(amount);
        if(BigDecimal.ZERO.compareTo(newBalance) > 0) {
            throw new UnauthorizedOperationException(ErrorTypeEnum.NEGATIVE_BALANCE);
        }
        account.setBalance(newBalance);
        transactionService.saveTransaction(TransactionTypeEnum.WITHDRAM, account, amount);
        return accountRepository.save(account);
    }
}
