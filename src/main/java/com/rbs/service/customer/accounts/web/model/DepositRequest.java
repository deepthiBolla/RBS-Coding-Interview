package com.rbs.service.customer.accounts.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositRequest {

    @NotBlank(message = "account number must not be null or empty")
    private final String accountNumber;
    private final BigDecimal amount;
    private final String customerId;

    @JsonCreator
    public DepositRequest(@JsonProperty String accountNumber, @JsonProperty BigDecimal amount, @JsonProperty String customerId) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepositRequest)) return false;
        DepositRequest that = (DepositRequest) o;
        return Objects.equals(getAccountNumber(), that.getAccountNumber()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getCustomerId(), that.getCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber(), getAmount(), getCustomerId());
    }

    @Override
    public String toString() {
        return "DepositRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", customerId='" + customerId + '\'' +
                '}';
    }

    public static final class Builder {

        private String accountNumber;
        private BigDecimal amount;
        private String customerId;

        private Builder() {
        }

        public static Builder aDepositAction() {
            return new Builder();
        }

        public Builder withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public DepositRequest build() {
            return new DepositRequest(accountNumber, amount, customerId);
        }
    }
}
