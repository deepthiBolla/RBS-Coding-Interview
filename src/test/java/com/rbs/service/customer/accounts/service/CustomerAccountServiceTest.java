package com.rbs.service.customer.accounts.service;

import com.rbs.service.customer.accounts.exceptions.CustomerAccountServiceException;
import com.rbs.service.customer.accounts.persistance.model.Account;
import com.rbs.service.customer.accounts.persistance.model.BankTransaction;
import com.rbs.service.customer.accounts.persistance.repository.AccountRepository;
import com.rbs.service.customer.accounts.persistance.repository.BankTransactionRepository;
import com.rbs.service.customer.accounts.service.impl.CustomerAccountServiceImpl;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.rbs.service.customer.accounts.CustomerAccountCommonConstants.*;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerAccountServiceTest {

    private String accountNumber;
    private String customerId;
    private String cardType;
    private BigDecimal accountBalance;
    private BigDecimal interestRate;
    private WithDrawlRequest withDrawlRequest;
    private DepositRequest depositRequest;
    private Account account;
    private List<BankTransaction> transactions;
    private Exception exception;


    @MockBean
    private BankTransactionRepository bankTransactionRepository;

    @MockBean
    private AccountRepository accountRepository;

    @InjectMocks
    CustomerAccountServiceImpl customerAccountService;

    @BeforeEach
     void setup() {
        customerAccountService.setAccountRepository(accountRepository);
        customerAccountService.setBankTransactionRepository(bankTransactionRepository);
    }

    @Test
    public void testWhenNullAccountIdForGetBalance() {
        givenWeHaveANullAccountNumberAndCustomerId();
        whenWeCallGetBalanceForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidAccountId();
    }

    @Test
    public void testWhenEmptyAccountIdForGetBalance() {
        givenWeHaveABlankAccountNumberAndCustomerId();
        whenWeCallGetBalanceForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidAccountId();
    }

    @Test
    public void testWhenGetBalanceHappyPath() {
        givenWeHaveAValidAccountNumberAndCustomerId();
        andAccountRepositoryReturnsBalanceForAccountNumber();
        whenWeCallGetBalanceForAccountNumber();
        thenBalanceIsReturned();
    }

    @Test
    public void testWhenInvalidWithDrawlRequestForWithDrawl() {
        givenWeHaveAnEmptyWithDrawlRequest();
        whenWeCallWithDrawlForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidRequestForWithDrawl();
    }

    @Test
    public void testWithDrawlForHappyPath() {
        givenWeHaveAValidDrawlRequest();
        andAccountRepositoryWithDrawsRequestedAmount();
        whenWeCallWithDrawlForAccountNumber();
        thenTheAmountIsWithDrawn();
    }

    @Test
    public void testWhenInvalidDepositRequestForWithDrawl() {
        givenWeHaveAnEmptyDepositRequest();
        whenWeCallDepositForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidRequestForDeposit();
    }

    @Test
    public void testDepositForHappyPath() {
        givenWeHaveAValidDepositRequest();
        andAccountRepositoryWithDepositRequestedAmount();
        whenWeCallDepositForAccountNumber();
        thenTheAmountIsDeposited();
    }

    @Test
    public void testWhenNullAccountIdForGetInterestRate() {
        givenWeHaveANullAccountNumberAndCardType();
        whenWeCallGetInterestRateForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidRequestForGetInterestRate();
    }

    @Test
    public void testWhenGetInterestRateHappyPath() {
        givenWeHaveAValidAccountNumberAndCardType();
        andAccountRepositoryReturnsInterestRateForAccountNumberAndCardType();
        whenWeCallGetInterestRateForAccountNumber();
        thenInterestRateIsReturned();
    }


    @Test
    public void testWhenNullAccountIdForGetTransactions() {
        givenWeHaveAValidAccountNumberAndCustomerId();
        whenWeCallGetTransactionsForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidRequestForGetTransactions();
    }

    private void givenWeHaveANullAccountNumberAndCustomerId() {
        accountNumber = null;
        customerId = null;
    }

    private void givenWeHaveANullAccountNumberAndCardType() {
        accountNumber = null;
        cardType = null;
    }

    private void givenWeHaveABlankAccountNumberAndCustomerId() {
        accountNumber = "";
        customerId = "";
    }

    private void givenWeHaveAValidAccountNumberAndCustomerId() {
        accountNumber = ACCOUNT_NUMBER;
        customerId = CUSTOMER_ID;
    }

    private void givenWeHaveAValidAccountNumberAndCardType() {
        accountNumber = ACCOUNT_NUMBER;
        cardType = CARD_TYPE;
    }


    private void givenWeHaveAnEmptyWithDrawlRequest() {
        withDrawlRequest = WithDrawlRequest.Builder.aWithDrawlAction().build();
    }

    private void givenWeHaveAValidDrawlRequest() {
        withDrawlRequest = WithDrawlRequest.Builder.aWithDrawlAction().withAccountNumber(ACCOUNT_NUMBER)
                .withCustomerId(CUSTOMER_ID).withAmount(WITHDRAWL_AMOUNT).build();
    }

    private void givenWeHaveAnEmptyDepositRequest() {
        depositRequest = DepositRequest.Builder.aDepositAction().build();
    }

    private void givenWeHaveAValidDepositRequest() {
        depositRequest = DepositRequest.Builder.aDepositAction().withAccountNumber(ACCOUNT_NUMBER)
                .withCustomerId(CUSTOMER_ID).withAmount(WITHDRAWL_AMOUNT).build();
    }

    private void andAccountRepositoryReturnsBalanceForAccountNumber() {
        Account account = new Account();
        account.setBalance(ACCOUNT_BALANCE);
        Optional<Account> optionalAccount = ofNullable(account);
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenReturn(optionalAccount);
    }

    private void andAccountRepositoryWithDrawsRequestedAmount() {
        Account account = new Account();
        account.setBalance(ACCOUNT_BALANCE);
        Account resultAccount = new Account();
        resultAccount.setBalance(ACCOUNT_BALANCE.subtract(new BigDecimal(10)));
        Optional<Account> optionalAccount = ofNullable(account);
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenReturn(optionalAccount);
        when(accountRepository.save(account)).thenReturn(resultAccount);
    }

    private void andAccountRepositoryWithDepositRequestedAmount() {
        Account account = new Account();
        account.setBalance(ACCOUNT_BALANCE);
        Account resultAccount = new Account();
        resultAccount.setBalance(ACCOUNT_BALANCE.add(new BigDecimal(10)));
        Optional<Account> optionalAccount = ofNullable(account);
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenReturn(optionalAccount);
        when(accountRepository.save(account)).thenReturn(resultAccount);
    }

    private void andAccountRepositoryReturnsInterestRateForAccountNumberAndCardType() {
        Account account = new Account();
        account.setBalance(ACCOUNT_BALANCE);
        Optional<Account> optionalAccount = ofNullable(account);
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenReturn(optionalAccount);
    }

    // TODO: 24/02/2020 custom repository method 'findAccountByIdAndActiveTrue' is not accessable from here. Should find a way to test this.
   /* private void andAccountRepositoryReturnsTransactionsForAccountNumberAndCardType() {
        Account account = new Account();
        account.setBalance(ACCOUNT_BALANCE);
        Optional<Account> optionalAccount = ofNullable(account);
        transactions.add(new BankTransaction());
       // when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER, 20)).thenReturn(optionalAccount);
    }*/

    private void whenWeCallGetBalanceForAccountNumber() {
        try {
            accountBalance = customerAccountService.getBalance(accountNumber, customerId);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallWithDrawlForAccountNumber() {
        try {
            account = customerAccountService.withDrawl(withDrawlRequest);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallDepositForAccountNumber() {
        try {
            account = customerAccountService.deposit(depositRequest);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallGetInterestRateForAccountNumber() {
        try {
            interestRate = customerAccountService.getInterestRate(accountNumber, CARD_TYPE, customerId);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallGetTransactionsForAccountNumber() {
        try {
            transactions = customerAccountService.getTransactions(accountNumber, 20, customerId);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void thenAnExceptionIsThrownForAnInvalidAccountId() {
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenThrow(CustomerAccountServiceException.class);
        assertEquals(String.format("No account found for account Number [%s]", accountNumber), exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForWithDrawl() {
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenThrow(CustomerAccountServiceException.class);
        assertEquals(
                String.format("Failed to locate account for withDrawl Request for account number [%s] as requested by customer [%s]",
                        withDrawlRequest.getAccountNumber(), withDrawlRequest.getCustomerId()),
                exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForDeposit() {
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenThrow(CustomerAccountServiceException.class);
        assertEquals(
                String.format("Failed to locate account for deposit Request for account number [%s] as requested by customer [%s]",
                        depositRequest.getAccountNumber(), depositRequest.getCustomerId()),
                exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForGetInterestRate() {
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenThrow(CustomerAccountServiceException.class);
        assertEquals(
                String.format("Failed to locate account to get interestRate for account number [%s] and card type [%s]",
                        accountNumber, CARD_TYPE),
                exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForGetTransactions() {
        when(accountRepository.findAccountByIdAndActiveTrue(ACCOUNT_NUMBER)).thenThrow(CustomerAccountServiceException.class);
        assertEquals(
                String.format("Failed to locate account to get transactions for account number [%s]", accountNumber),
                exception.getMessage());
    }

    private void thenBalanceIsReturned() {
        assertNotNull(accountBalance);
        assertNull(exception);
        assertEquals(accountBalance, ACCOUNT_BALANCE);
    }

    private void thenTheAmountIsWithDrawn() {
        assertNotNull(account);
        assertNull(exception);
        assertEquals(90, account.getBalance().intValue());
    }

    private void thenTheAmountIsDeposited() {
        assertNotNull(account);
        assertNull(exception);
        assertEquals(110, account.getBalance().intValue());
    }

    private void thenInterestRateIsReturned() {
        assertNotNull(interestRate);
        assertNull(exception);
    }

}
