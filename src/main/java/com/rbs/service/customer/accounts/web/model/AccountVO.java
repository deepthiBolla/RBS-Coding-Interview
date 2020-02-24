package com.rbs.service.customer.accounts.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rbs.service.customer.accounts.persistance.model.AccountType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountVO implements Serializable {

    private String accountNumber;
    private AccountType accountType;
    private boolean active;
    private BigDecimal balance;

    public AccountVO() {
    }

    @JsonCreator
    public AccountVO(@JsonProperty String accountNumber, @JsonProperty AccountType accountType, @JsonProperty boolean active, @JsonProperty BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.active = active;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountVO)) return false;
        AccountVO accountVO = (AccountVO) o;
        return isActive() == accountVO.isActive() &&
                Objects.equals(getAccountNumber(), accountVO.getAccountNumber()) &&
                Objects.equals(getAccountType(), accountVO.getAccountType()) &&
                Objects.equals(getBalance(), accountVO.getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber(), getAccountType(), isActive(), getBalance());
    }

    @Override
    public String toString() {
        return "AccountVO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", active=" + active +
                ", balance=" + balance +
                '}';
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return active;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public static final class Builder {

        private String accountNumber;
        private AccountType accountType;
        private boolean active;
        private BigDecimal balance;

        private Builder() {
        }

        public static Builder anAccountVO() {
            return new Builder();
        }

        public Builder withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder withAccountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder withActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public AccountVO build() {
            return new AccountVO(accountNumber, accountType, active, balance);
        }
    }

}
