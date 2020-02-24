package com.rbs.service.customer.accounts.application;


import com.rbs.service.customer.accounts.web.model.AccountVO;
import com.rbs.service.customer.accounts.web.model.CustomerTransactionVo;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerAccountDelegator {

    BigDecimal getBalance(final String accountNumber, final String customerId);

    AccountVO withDrawl(final WithDrawlRequest withDrawl);

    AccountVO deposit(final DepositRequest depositAction);

    BigDecimal getInterestRate(final String accountNumber, final String cardType, final String customerId);

    List<CustomerTransactionVo> getTransactions(final String accountNumber, final int numberOfTransactions,  final String customerId);
}
