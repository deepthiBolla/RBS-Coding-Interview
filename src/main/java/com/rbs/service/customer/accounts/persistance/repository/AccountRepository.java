package com.rbs.service.customer.accounts.persistance.repository;

import com.rbs.service.customer.accounts.persistance.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {
    Optional<Account> findAccountByIdAndActiveTrue(String accountNumber);
}
