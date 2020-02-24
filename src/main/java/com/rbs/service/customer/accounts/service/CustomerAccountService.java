package com.rbs.service.customer.accounts.service;

import com.rbs.service.customer.accounts.exceptions.CustomerAccountNotFoundException;
import com.rbs.service.customer.accounts.persistance.model.Account;
import com.rbs.service.customer.accounts.persistance.model.BankTransaction;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerAccountService {

    BigDecimal getBalance(final String accountNumber, final String customerId) throws CustomerAccountNotFoundException;

    Account withDrawl(final WithDrawlRequest withDrawl) throws CustomerAccountNotFoundException;

    Account deposit(final DepositRequest depositAction) throws CustomerAccountNotFoundException;

    BigDecimal getInterestRate(final String accountNumber, final String cardType, final String customerId) throws CustomerAccountNotFoundException;

    List<BankTransaction> getTransactions(final String accountNumber, final int numberOfTransactions, final String customerId) throws CustomerAccountNotFoundException;
}
