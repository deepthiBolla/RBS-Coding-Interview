package com.rbs.service.customer.accounts.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "BANK_TRANSACTION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BankTransaction{

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "bank_transaction_Sequence")
    @SequenceGenerator(name = "bank_transaction_Sequence", sequenceName = "BANK_TRANSACTION_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_account_id")
    @JsonIgnore
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_account_id")
    @JsonIgnore
    private Account toAccount;

    private String description;

    private BigDecimal amount;

    private LocalDate transactionDate;

    public BankTransaction() {
    }

    public BankTransaction(Long id, Account fromAccount, Account toAccount, String description, BigDecimal amount, LocalDate transactionDate) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
