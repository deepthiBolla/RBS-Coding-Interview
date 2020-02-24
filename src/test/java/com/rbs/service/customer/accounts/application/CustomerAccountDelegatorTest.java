package com.rbs.service.customer.accounts.application;

import com.rbs.service.customer.accounts.application.impl.CustomerAccountDelegatorImpl;
import com.rbs.service.customer.accounts.persistance.model.Account;
import com.rbs.service.customer.accounts.persistance.model.BankTransaction;
import com.rbs.service.customer.accounts.service.CustomerAccountService;
import com.rbs.service.customer.accounts.web.model.AccountVO;
import com.rbs.service.customer.accounts.web.model.CustomerTransactionVo;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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
public class CustomerAccountDelegatorTest {

    private String accountNumber;
    private String customerId;
    private String cardType;
    private BigDecimal accountBalance;
    private BigDecimal interestRate;
    private WithDrawlRequest withDrawlRequest;
    private DepositRequest depositRequest;
    private Account account;
    private AccountVO accountVo;
    private List<BankTransaction> transactions;
    private List<CustomerTransactionVo> customerTransactionVoList;
    private Exception exception;


    @MockBean
    private CustomerAccountService customerAccountService;

    @InjectMocks
    CustomerAccountDelegatorImpl customerAccountDelegator;

    @BeforeEach
     void setup() {
        customerAccountDelegator.setCustomerAccountService(customerAccountService);
    }

    @Test
    public void testWhenEmptyAccountIdForGetBalance() {
        givenWeHaveANullAccountNumberAndCustomerId();
        whenWeCallGetBalanceForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidAccountId();
    }

    @Test
    public void testWhenGetBalanceHappyPath() {
        givenWeHaveAValidAccountNumberAndCustomerId();
        andAccountServiceReturnsBalanceForAccountNumber();
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
        andAccountServiceWithDrawsRequestedAmount();
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
        andAccountServiceWithDepositRequestedAmount();
        whenWeCallDepositForAccountNumber();
        thenTheAmountIsDeposited();
    }

    @Test
    public void testWhenEmptyIdForGetInterestRate() {
        givenWeHaveANullAccountNumberAndCardType();
        whenWeCallGetInterestRateForAccountNumber();
        thenAnExceptionIsThrownForAnInvalidRequestForGetInterestRate();
    }

    @Test
    public void testWhenGetInterestRateHappyPath() {
        givenWeHaveAValidAccountNumberAndCardType();
        andAccountServiceReturnsInterestRateForAccountNumberAndCardType();
        whenWeCallGetInterestRateForAccountNumber();
    }


    @Test
    public void testWhenEmptyAccountIdForGetTransactions() {
        givenWeHaveABlankAccountNumberAndCustomerId();
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

    private void andAccountServiceReturnsBalanceForAccountNumber() {
        when(customerAccountService.getBalance(ACCOUNT_NUMBER, CUSTOMER_ID)).thenReturn(ACCOUNT_BALANCE);
    }

    private void andAccountServiceWithDrawsRequestedAmount() {
        Account account = new Account();
        account.setBalance(ACCOUNT_BALANCE);
        Account resultAccount = new Account();
        resultAccount.setBalance(ACCOUNT_BALANCE.subtract(new BigDecimal(10)));
        Optional<Account> optionalAccount = ofNullable(account);
        when(customerAccountService.withDrawl(withDrawlRequest)).thenReturn(resultAccount);
    }

    private void andAccountServiceWithDepositRequestedAmount() {
        Account account = new Account();
        account.setBalance(ACCOUNT_BALANCE);
        Account resultAccount = new Account();
        resultAccount.setBalance(ACCOUNT_BALANCE.add(new BigDecimal(10)));
        when(customerAccountService.deposit(depositRequest)).thenReturn(resultAccount);
    }

    private void andAccountServiceReturnsInterestRateForAccountNumberAndCardType() {
        when(customerAccountService.getInterestRate(ACCOUNT_NUMBER, CARD_TYPE, CUSTOMER_ID)).thenReturn(CREDIT_INTEREST_RATE);
    }

    private void whenWeCallGetBalanceForAccountNumber() {
        try {
            accountBalance = customerAccountDelegator.getBalance(accountNumber, customerId);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallWithDrawlForAccountNumber() {
        try {
            accountVo = customerAccountDelegator.withDrawl(withDrawlRequest);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallDepositForAccountNumber() {
        try {
            accountVo = customerAccountDelegator.deposit(depositRequest);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallGetInterestRateForAccountNumber() {
        try {
            interestRate = customerAccountDelegator.getInterestRate(accountNumber, CARD_TYPE, customerId);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void whenWeCallGetTransactionsForAccountNumber() {
        try {
            customerTransactionVoList = customerAccountDelegator.getTransactions(accountNumber, 20, customerId);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    private void thenAnExceptionIsThrownForAnInvalidAccountId() {
        when(customerAccountService.getBalance(ACCOUNT_NUMBER, CUSTOMER_ID)).thenThrow(IllegalArgumentException.class);
        assertEquals("account Number must not be null or empty", exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForWithDrawl() {
        when(customerAccountService.withDrawl(withDrawlRequest)).thenThrow(IllegalArgumentException.class);
        assertEquals("account Number must not be null or empty",
                exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForDeposit() {
        when(customerAccountService.deposit(depositRequest)).thenThrow(IllegalArgumentException.class);
        assertEquals("account Number must not be null or empty",
                exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForGetInterestRate() {
        when(customerAccountService.getInterestRate(ACCOUNT_NUMBER, CARD_TYPE, CUSTOMER_ID)).thenThrow(IllegalArgumentException.class);
        assertEquals("account Number must not be null or empty",
                exception.getMessage());
    }

    private void thenAnExceptionIsThrownForAnInvalidRequestForGetTransactions() {
        when(customerAccountService.getTransactions(ACCOUNT_NUMBER, 20, CUSTOMER_ID)).thenThrow(IllegalArgumentException.class);
        assertEquals("account Number must not be null or empty",
                exception.getMessage());
    }

    private void thenBalanceIsReturned() {
        assertNotNull(accountBalance);
        assertNull(exception);
        assertEquals(accountBalance, ACCOUNT_BALANCE);
    }

    private void thenTheAmountIsWithDrawn() {
        assertNotNull(accountVo);
        assertNull(exception);
        assertEquals(90, accountVo.getBalance().intValue());
    }

    private void thenTheAmountIsDeposited() {
        assertNull(exception);
        assertEquals(110, accountVo.getBalance().intValue());
    }


}
