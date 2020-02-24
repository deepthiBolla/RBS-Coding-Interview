package com.rbs.service.customer.accounts.service.impl;

import com.rbs.service.customer.accounts.exceptions.CustomerAccountNotFoundException;
import com.rbs.service.customer.accounts.exceptions.CustomerAccountServiceException;
import com.rbs.service.customer.accounts.persistance.model.Account;
import com.rbs.service.customer.accounts.persistance.model.BankCard;
import com.rbs.service.customer.accounts.persistance.model.BankTransaction;
import com.rbs.service.customer.accounts.persistance.repository.AccountRepository;
import com.rbs.service.customer.accounts.persistance.repository.BankTransactionRepository;
import com.rbs.service.customer.accounts.service.CustomerAccountService;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

@Service
@Transactional
public class CustomerAccountServiceImpl implements CustomerAccountService {

    Logger logger = LogManager.getLogger(CustomerAccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Override
    public BigDecimal getBalance(final String accountNumber, final String customerId) throws CustomerAccountNotFoundException {
        logger.debug("getting account balance for account [{}] as requested by customer Id [{}] ", accountNumber, customerId);

        Account account = new Account();

        try {
            account = accountRepository.findAccountByIdAndActiveTrue(accountNumber).get();

        } catch (NoSuchElementException noSuchElementException) {
            throw new CustomerAccountNotFoundException(
                    String.format("No account found for account Number [%s]", accountNumber));
        } catch (Exception exception) {
            throw new CustomerAccountServiceException(
                    String.format("Failed to get account balance for account Number [%s]", accountNumber), exception.getCause());
        }
        return account.getBalance();
    }

    @Override
    public Account withDrawl(final WithDrawlRequest withDrawlRequest) throws CustomerAccountNotFoundException {
        logger.debug("processing withDrawl request for account [{}] as requested by customer Id [{}] ",
                withDrawlRequest.getAccountNumber(), withDrawlRequest.getCustomerId());

        Account account = new Account();

        try {
            account = accountRepository.findAccountByIdAndActiveTrue(withDrawlRequest.getAccountNumber()).get();
            BigDecimal currentBalance = account.getBalance();
            account.setBalance(currentBalance.subtract(withDrawlRequest.getAmount()));
            accountRepository.save(account);

        } catch (NoSuchElementException noSuchElementException) {
            throw new CustomerAccountNotFoundException(
                    String.format("Failed to locate account for withDrawl Request for account number [%s] as requested by customer [%s]",
                            withDrawlRequest.getAccountNumber(),
                            withDrawlRequest.getCustomerId()));
        } catch (Exception exception) {
            throw new CustomerAccountServiceException(
                    String.format("Failed to withDraw amount as for account number [%s] as requested by customer [%s]",
                            withDrawlRequest.getAccountNumber(),
                            withDrawlRequest.getCustomerId()), exception.getCause());
        }
        return account;
    }

    @Override
    public Account deposit(final DepositRequest depositRequest) throws CustomerAccountNotFoundException {
        logger.debug("processing deposit request for account [{}] as requested by customer Id [{}] ",
                depositRequest.getAccountNumber(), depositRequest.getCustomerId());

        Account account = new Account();

        try {
            account = accountRepository.findAccountByIdAndActiveTrue(depositRequest.getAccountNumber()).get();
            BigDecimal currentBalance = account.getBalance();
            account.setBalance(currentBalance.add(depositRequest.getAmount()));
            accountRepository.save(account);
        } catch (NoSuchElementException noSuchElementException) {
            throw new CustomerAccountNotFoundException(
                    String.format("Failed to locate account for deposit Request for account number [%s] as requested by customer [%s]",
                            depositRequest.getAccountNumber(),
                            depositRequest.getCustomerId()));
        } catch (Exception exception) {
            throw new CustomerAccountServiceException(
                    String.format("Failed to deposit amount as for account number [%s] as requested by customer [%s]",
                            depositRequest.getAccountNumber(),
                            depositRequest.getCustomerId()), exception.getCause());
        }
        return account;
    }

    @Override
    public BigDecimal getInterestRate(final String accountNumber, final String cardType, final String customerId) throws CustomerAccountNotFoundException {
        try {
            Account account  = accountRepository.findAccountByIdAndActiveTrue(accountNumber).get();
            Set<BankCard> customerCards = account.getBankCards();

            Predicate<BankCard> cardForAccountPredicate = card -> card.getAccount().getId().equalsIgnoreCase(accountNumber);
            Optional<BankCard> card = customerCards.stream().filter(cardForAccountPredicate).findFirst();
            return card.isPresent() ? card.get().getCardType().getInterestRate() : BigDecimal.ZERO;

        } catch (NoSuchElementException noSuchElementException) {
            throw new CustomerAccountNotFoundException(String.format("Failed to locate account to get interestRate for account number [%s] and card type [%s]", accountNumber, cardType));
        } catch (Exception exception) {
            throw new CustomerAccountServiceException(String.format("Failed to get interestRate for account number [%s] and card type [%s]", accountNumber, cardType), exception.getCause());
        }
    }

    @Override
    public List<BankTransaction> getTransactions(String accountNumber, int numberOfTransactions, final String customerId) throws CustomerAccountNotFoundException {
        List<BankTransaction> transactions = Collections.emptyList();
        try {
            Account account = accountRepository.findAccountByIdAndActiveTrue(accountNumber).get();
            transactions = bankTransactionRepository.findAccountTransactions(account.getId(), numberOfTransactions);
        } catch (NoSuchElementException noSuchElementException) {
            throw new CustomerAccountNotFoundException(String.format("Failed to locate account to get transactions for account number [%s]", accountNumber));
        } catch (Exception exception) {
            throw new CustomerAccountServiceException(String.format("Failed to get transactions for account number [%s]", accountNumber), exception.getCause());
        }
        return transactions;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void setBankTransactionRepository(BankTransactionRepository bankTransactionRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
    }
}




