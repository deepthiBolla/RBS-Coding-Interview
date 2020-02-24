package com.rbs.service.customer.accounts.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account{

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "account_Sequence")
    @SequenceGenerator(name = "account_Sequence", sequenceName = "ACCOUNT_SEQ")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id")
    @JsonIgnore
    private AccountType accountType;

    private boolean active;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "account" , fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BankCard> bankCards  = new HashSet<>();

    @OneToMany(mappedBy = "toAccount", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BankTransaction> incomingTransactions  = new HashSet<>();

    @OneToMany(mappedBy = "fromAccount", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BankTransaction> outgoingTransactions  = new HashSet<>();

    public Account() {
    }

    public Account(String id) {
        this.id = id;
    }

    public Account(String id, AccountType accountType, boolean active, BigDecimal balance, Customer customer, Set<BankCard> bankCards, Set<BankTransaction> incomingTransactions, Set<BankTransaction> outgoingTransactions) {
        this.id = id;
        this.accountType = accountType;
        this.active = active;
        this.balance = balance;
        this.customer = customer;
        this.bankCards = bankCards;
        this.incomingTransactions = incomingTransactions;
        this.outgoingTransactions = outgoingTransactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(Set<BankCard> bankCards) {
        this.bankCards = bankCards;
    }

    public Set<BankTransaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public void setIncomingTransactions(Set<BankTransaction> incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
    }

    public Set<BankTransaction> getOutgoingTransactions() {
        return outgoingTransactions;
    }

    public void setOutgoingTransactions(Set<BankTransaction> outgoingTransactions) {
        this.outgoingTransactions = outgoingTransactions;
    }
}
