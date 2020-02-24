package com.rbs.service.customer.accounts.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "CARD_TYPE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CardType{

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "card_type_Sequence")
    @SequenceGenerator(name = "card_type_Sequence", sequenceName = "CARD_TYPE_SEQ")
    private Long id;

    private String type;
    private String description;
    private BigDecimal interestRate;

    @OneToMany(mappedBy = "cardType", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BankCard> customerCards;

    public CardType() {
    }

    public CardType(Long id, String type, String description, BigDecimal interestRate, Set<BankCard> customerCards) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.interestRate = interestRate;
        this.customerCards = customerCards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Set<BankCard> getCustomerCards() {
        return customerCards;
    }

    public void setCustomerCards(Set<BankCard> customerCards) {
        this.customerCards = customerCards;
    }
}
