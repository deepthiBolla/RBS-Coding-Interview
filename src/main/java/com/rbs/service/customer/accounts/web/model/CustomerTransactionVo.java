package com.rbs.service.customer.accounts.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rbs.service.customer.accounts.persistance.model.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTransactionVo {

    private Long id;
    private Account fromAccount;
    private Account toAccount;
    private String description;
    private BigDecimal amount;
    private LocalDate transactionDateTime;

    @JsonCreator
    public CustomerTransactionVo(@JsonProperty Long id, @JsonProperty Account fromAccount, @JsonProperty Account toAccount, @JsonProperty String description, @JsonProperty BigDecimal amount, @JsonProperty LocalDate transactionDateTime) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.description = description;
        this.amount = amount;
        this.transactionDateTime = transactionDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerTransactionVo)) return false;
        CustomerTransactionVo that = (CustomerTransactionVo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fromAccount, that.fromAccount) &&
                Objects.equals(toAccount, that.toAccount) &&
                Objects.equals(description, that.description) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(transactionDateTime, that.transactionDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAccount, toAccount, description, amount, transactionDateTime);
    }

    @Override
    public String toString() {
        return "customerTransactionVo{" +
                "id=" + id +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", transactionDateTime=" + transactionDateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDateTime() {
        return transactionDateTime;
    }

    public static final class Builder {

        private Long id;
        private Account fromAccount;
        private Account toAccount;
        private String description;
        private BigDecimal amount;
        private LocalDate transactionDateTime;

        private Builder() {
        }

        public static Builder aCustomerTransactionVO() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withFromAccount(Account fromAccount) {
            this.fromAccount = fromAccount;
            return this;
        }

        public Builder withToAccount(Account toAccount) {
            this.toAccount = toAccount;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withTransactionDateTime(LocalDate transactionDateTime) {
            this.transactionDateTime = transactionDateTime;
            return this;
        }

        public CustomerTransactionVo build() {
            return new CustomerTransactionVo(id, fromAccount, toAccount, description, amount, transactionDateTime);
        }

    }
}