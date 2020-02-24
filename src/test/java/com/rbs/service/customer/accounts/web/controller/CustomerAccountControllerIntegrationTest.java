package com.rbs.service.customer.accounts.web.controller;

import com.rbs.service.customer.accounts.application.CustomerAccountDelegator;
import com.rbs.service.customer.accounts.exceptions.CustomerAccountNotFoundException;
import com.rbs.service.customer.accounts.exceptions.CustomerAccountServiceException;
import com.rbs.service.customer.accounts.web.model.AccountVO;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.rbs.service.customer.accounts.CustomerAccountCommonConstants.*;
import static com.rbs.service.customer.accounts.web.model.AccountVO.Builder.anAccountVO;
import static com.rbs.service.customer.accounts.web.model.DepositRequest.Builder.aDepositAction;
import static com.rbs.service.customer.accounts.web.model.WithDrawlRequest.Builder.aWithDrawlAction;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerAccountController.class)
public class CustomerAccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private MvcResult result;
    private static WithDrawlRequest withDrawlRequest;
    private static DepositRequest depositRequest;
    private static AccountVO accountVO;

    @MockBean
    private CustomerAccountDelegator customerAccountDelegator;

    @BeforeAll
    public static void setup() {
        withDrawlRequest = createWithDrawlRequest();
        accountVO = createAccountVo();
        depositRequest = createDepositRequest();
    }

    @Test
    public void testBalanceEnquiryForAccountNumberThrowsApplicationException() throws Exception {
        givenBalanceEnquiryForAccountNumberThrowsAnApplicationException();
        whenWeDoABalanceEnquiryForCustomerAccountService(status().isInternalServerError());
    }

    @Test
    public void testBalanceEnquiryForAccountNumberThrowsAccountNotFoundException() throws Exception {
        givenBalanceEnquiryForAccountNumberThrowsAccountNotFoundException();
        whenWeDoABalanceEnquiryForCustomerAccountService(status().isNotFound());
    }

    @Test
    public void testBalanceEnquiryHappyPath() throws Exception {
        givenBalanceEnquiryForAccountNumberGivesAValidAccountBalance();
        whenWeDoABalanceEnquiryForCustomerAccountService(status().isOk());
        thenAccountBalanceIsReturnedViaTheCustomerAccountApplication();
    }

    @Test
    public void testWithDrawlThrowsApplicationException() throws Exception {
        givenWithDrawlThrowsAnApplicationException();
        whenWeRequestWithDrawlForCustomerAccountService(status().isInternalServerError());
    }

    @Test
    public void testWithDrawlThrowsAccountNotFoundException() throws Exception {
        givenWithDrawlThrowsAccountNotFoundException();
        whenWeRequestWithDrawlForCustomerAccountService(status().isNotFound());
    }

    @Test
    public void testWithDrawlHappyPath() throws Exception {
        givenWithDrawlGivesAValidAccountBalance();
        whenWeRequestWithDrawlForCustomerAccountService(status().isOk());
        thenTheCustomerAccountApplicationIsWithDrawn();
    }

    @Test
    public void testDepositThrowsApplicationException() throws Exception {
        givenDepositThrowsAnApplicationException();
        whenWeRequestDepositForCustomerAccountService(status().isInternalServerError());
    }

    @Test
    public void testDepositThrowsAccountNotFoundException() throws Exception {
        givenDepositThrowsAccountNotFoundException();
        whenWeRequestDepositForCustomerAccountService(status().isNotFound());
    }

    @Test
    public void testDepositHappyPath() throws Exception {
        givenDepositGivesAValidAccountBalance();
        whenWeRequestDepositForCustomerAccountService(status().isOk());
        thenAmountIsDepositedToTheCustomerAccount();
    }

    @Test
    public void testGetInterestRateThrowsApplicationException() throws Exception {
        givenGetInterestRateThrowsAnApplicationException();
        whenWeDoAInterestRateCheckForCustomerACustomerCard(status().isInternalServerError());
    }

    @Test
    public void testGetInterestRateThrowsAccountNotFoundException() throws Exception {
        givenGetInterestRateThrowsAccountNotFoundException();
        whenWeDoAInterestRateCheckForCustomerACustomerCard(status().isNotFound());
    }

    @Test
    public void testGetInterestRateHappyPath() throws Exception {
        givenGetInterestRateGivesAValidAccountBalance();
        whenWeDoAInterestRateCheckForCustomerACustomerCard(status().isOk());
        thenInterestRateIsReturnedViaTheCustomerAccountApplication();
    }

    private void thenAccountBalanceIsReturnedViaTheCustomerAccountApplication() {
        verify(customerAccountDelegator, times(1)).getBalance(ACCOUNT_NUMBER, CUSTOMER_ID);
    }

    private void thenTheCustomerAccountApplicationIsWithDrawn() {
        verify(customerAccountDelegator, times(1)).withDrawl(withDrawlRequest);
    }

    private void thenAmountIsDepositedToTheCustomerAccount() {
        verify(customerAccountDelegator, times(1)).deposit(depositRequest);
    }

    private void thenInterestRateIsReturnedViaTheCustomerAccountApplication() {
        verify(customerAccountDelegator, times(1)).getInterestRate(ACCOUNT_NUMBER, CARD_TYPE, CUSTOMER_ID);
    }

    private void givenWithDrawlThrowsAnApplicationException() {
        when(customerAccountDelegator.withDrawl(withDrawlRequest))
                .thenThrow(CustomerAccountServiceException.class);
    }

    private void givenWithDrawlThrowsAccountNotFoundException() {
        when(customerAccountDelegator.withDrawl(withDrawlRequest))
                .thenThrow(CustomerAccountNotFoundException.class);
    }

    private void givenWithDrawlGivesAValidAccountBalance() {
        when(customerAccountDelegator.withDrawl(withDrawlRequest))
                .thenReturn(accountVO);
    }

    private void givenDepositThrowsAnApplicationException() {
        when(customerAccountDelegator.deposit(depositRequest))
                .thenThrow(CustomerAccountServiceException.class);
    }

    private void givenDepositThrowsAccountNotFoundException() {
        when(customerAccountDelegator.deposit(depositRequest))
                .thenThrow(CustomerAccountNotFoundException.class);
    }

    private void givenDepositGivesAValidAccountBalance() {
        when(customerAccountDelegator.deposit(depositRequest))
                .thenReturn(accountVO);
    }

    private void givenGetInterestRateThrowsAnApplicationException() {
        when(customerAccountDelegator.getInterestRate(ACCOUNT_NUMBER, CARD_TYPE, CUSTOMER_ID))
                .thenThrow(CustomerAccountServiceException.class);
    }

    private void givenGetInterestRateThrowsAccountNotFoundException() {
        when(customerAccountDelegator.getInterestRate(ACCOUNT_NUMBER, CARD_TYPE, CUSTOMER_ID))
                .thenThrow(CustomerAccountNotFoundException.class);
    }

    private void givenGetInterestRateGivesAValidAccountBalance() {
        when(customerAccountDelegator.getInterestRate(ACCOUNT_NUMBER, CARD_TYPE, CUSTOMER_ID))
                .thenReturn(CREDIT_INTEREST_RATE);
    }

    private void givenBalanceEnquiryForAccountNumberThrowsAnApplicationException() {
        when(customerAccountDelegator.getBalance(ACCOUNT_NUMBER, CUSTOMER_ID))
                .thenThrow(CustomerAccountServiceException.class);
    }

    private void givenBalanceEnquiryForAccountNumberThrowsAccountNotFoundException() {
        when(customerAccountDelegator.getBalance(ACCOUNT_NUMBER, CUSTOMER_ID))
                .thenThrow(CustomerAccountNotFoundException.class);
    }

    private void givenBalanceEnquiryForAccountNumberGivesAValidAccountBalance() {
        when(customerAccountDelegator.getBalance(ACCOUNT_NUMBER, CUSTOMER_ID))
                .thenReturn(ACCOUNT_BALANCE);
    }

    private void whenWeDoABalanceEnquiryForCustomerAccountService(ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accounts/" + ACCOUNT_NUMBER + "/balance")
                                                             .param(CUSTOMERID_PARAM, CUSTOMER_ID);

        result = mockMvc.perform(requestBuilder).andExpect(resultMatcher).andReturn();
    }

    private void whenWeRequestWithDrawlForCustomerAccountService(ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/accounts/withDrawl")
                                                                .requestAttr("customerId", CUSTOMER_ID)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .content(getWithDrawlRequestInJson(withDrawlRequest))
                                                                .accept(MediaType.APPLICATION_JSON);

        result = mockMvc.perform(requestBuilder).andExpect(resultMatcher).andReturn();
    }

    private void whenWeRequestDepositForCustomerAccountService(ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/accounts/deposit")
                                                            .content(getDepositRequestInJson(depositRequest))
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .accept(MediaType.APPLICATION_JSON);

        result = mockMvc.perform(requestBuilder).andExpect(resultMatcher).andReturn();
    }

    private void whenWeDoAInterestRateCheckForCustomerACustomerCard(ResultMatcher resultMatcher) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/accounts/" + ACCOUNT_NUMBER + "/" + CARD_TYPE + "/interestRate").requestAttr("customerId", CUSTOMER_ID)
                .param(CUSTOMERID_PARAM, CUSTOMER_ID);

        result = mockMvc.perform(requestBuilder).andExpect(resultMatcher).andReturn();
    }

    private String getWithDrawlRequestInJson(WithDrawlRequest withDrawlAction) {
        return "{\"accountNumber\":\"" + withDrawlAction.getAccountNumber() + "\", \"amount\":\"" + withDrawlAction.getAmount() + "\"}";
    }

    private String getDepositRequestInJson(DepositRequest depositAction) {
        return "{\"accountNumber\":\"" + depositAction.getAccountNumber() + "\", \"amount\":\"" + depositAction.getAmount() + "\"}";
    }

    private static WithDrawlRequest createWithDrawlRequest() {
        return aWithDrawlAction().withAccountNumber(ACCOUNT_NUMBER)
                .withAmount(WITHDRAWL_AMOUNT)
                .build();
    }

    private static AccountVO createAccountVo() {
        return anAccountVO().withAccountNumber(ACCOUNT_NUMBER)
                .withBalance(ACCOUNT_BALANCE)
                .build();
    }

    private static DepositRequest createDepositRequest() {
        return aDepositAction().withAccountNumber(ACCOUNT_NUMBER)
                .withAmount(DEPOSIT_AMOUNT)
                .build();
    }
}
