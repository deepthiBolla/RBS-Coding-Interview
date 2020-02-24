package com.rbs.service.customer.accounts.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "BANK_CARD")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BankCard {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "bank_card_Sequence")
    @SequenceGenerator(name = "bank_card_Sequence", sequenceName = "BANK_CARD_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_type_id")
    @JsonIgnore
    private CardType cardType;

    private String customerName;
    private LocalDate validFrom;
    private LocalDate expiryEnd;
    private String securityCode;
    private BigDecimal interestRate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    public BankCard() {
    }

    public BankCard(Long id, CardType cardType, String customerName, LocalDate validFrom, LocalDate expiryEnd, String securityCode, BigDecimal interestRate, Account account) {
        this.id = id;
        this.cardType = cardType;
        this.customerName = customerName;
        this.validFrom = validFrom;
        this.expiryEnd = expiryEnd;
        this.securityCode = securityCode;
        this.interestRate = interestRate;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getExpiryEnd() {
        return expiryEnd;
    }

    public void setExpiryEnd(LocalDate expiryEnd) {
        this.expiryEnd = expiryEnd;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
