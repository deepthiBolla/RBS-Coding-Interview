package com.rbs.service.customer.accounts.application;

import com.rbs.service.customer.accounts.persistance.model.Account;
import com.rbs.service.customer.accounts.persistance.model.BankTransaction;
import com.rbs.service.customer.accounts.web.model.AccountVO;
import com.rbs.service.customer.accounts.web.model.CustomerTransactionVo;

import static com.rbs.service.customer.accounts.web.model.CustomerTransactionVo.Builder.aCustomerTransactionVO;

public class EntityTranslator {

    public static AccountVO getAccountVOFor(final Account account) {
        return AccountVO.Builder.anAccountVO().withAccountNumber(account.getId())
                .withAccountType(account.getAccountType())
                .withActive(account.isActive())
                .withBalance(account.getBalance())
                .build();
    }

    public static CustomerTransactionVo getCustomerTransactionVO(final BankTransaction bankTransaction) {
        return aCustomerTransactionVO().withId(bankTransaction.getId())
                                        .withAmount(bankTransaction.getAmount())
                                        .withDescription(bankTransaction.getDescription())
                                        .withFromAccount(bankTransaction.getFromAccount())
                                        .withToAccount(bankTransaction.getToAccount())
                                        .withTransactionDateTime(bankTransaction.getTransactionDate())
                                        .build();
    }
}
