package com.rbs.service.customer.accounts.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT_TYPE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountType{

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "account_type_Sequence")
    @SequenceGenerator(name = "account_type_Sequence", sequenceName = "ACCOUNT_TYPE_SEQ")
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "accountType", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Account> accounts = new HashSet<>();

    public AccountType() {
    }

    public AccountType(Long id, String name, String description, Set<Account> accounts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
