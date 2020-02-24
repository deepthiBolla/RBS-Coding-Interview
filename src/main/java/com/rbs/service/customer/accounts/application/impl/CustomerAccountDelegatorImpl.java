package com.rbs.service.customer.accounts.application.impl;

import com.rbs.service.customer.accounts.persistance.model.Account;
import com.rbs.service.customer.accounts.persistance.model.BankTransaction;
import com.rbs.service.customer.accounts.web.model.AccountVO;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;
import com.rbs.service.customer.accounts.application.CustomerAccountDelegator;
import com.rbs.service.customer.accounts.application.EntityTranslator;
import com.rbs.service.customer.accounts.web.model.CustomerTransactionVo;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CustomerAccountDelegatorImpl implements CustomerAccountDelegator {

    @Autowired
    private CustomerAccountService customerAccountService;

    @Override
    public BigDecimal getBalance(final String accountNumber, final String customerId) {
        Assert.hasText(accountNumber, "account Number must not be null or empty");
        Assert.hasText(customerId, "customer Id must not be null or empty");

        return customerAccountService.getBalance(accountNumber, customerId);
    }

    @Override
    public AccountVO withDrawl(final WithDrawlRequest withDrawl) {
        Assert.hasText(withDrawl.getAccountNumber(), "account Number must not be null or empty");
        Assert.hasText(withDrawl.getCustomerId(), "customer Id must not be null or empty");

        Account account = customerAccountService.withDrawl(withDrawl);
        return EntityTranslator.getAccountVOFor(account);
    }

    @Override
    public AccountVO deposit(final DepositRequest depositRequest) {
        Assert.hasText(depositRequest.getAccountNumber(), "account Number must not be null or empty");
        Assert.hasText(depositRequest.getCustomerId(), "customer Id must not be null or empty");

        Account account = customerAccountService.deposit(depositRequest);
        return EntityTranslator.getAccountVOFor(account);
    }

    @Override
    public BigDecimal getInterestRate(final String accountNumber, final String cardType, final String customerId) {
        Assert.hasText(accountNumber, "account Number must not be null or empty");
        Assert.hasText(customerId, "customer Id must not be null or empty");

        return customerAccountService.getInterestRate(accountNumber, cardType, customerId);
    }

    @Override
    public List<CustomerTransactionVo> getTransactions(String accountNumber, int numberOfTransactions, final String customerId) {
        Assert.hasText(accountNumber, "account Number must not be null or empty");
        Assert.hasText(customerId, "customer Id must not be null or empty");

        List<BankTransaction> transactions = customerAccountService.getTransactions(accountNumber, numberOfTransactions, customerId);
        return transactions.stream().map(EntityTranslator::getCustomerTransactionVO).collect(toList());
    }

    public void setCustomerAccountService(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }
}
