package com.rbs.service.customer.accounts.persistance.repository;

import com.rbs.service.customer.accounts.persistance.model.BankCard;
import org.springframework.data.repository.CrudRepository;

public interface BankCardRepository extends CrudRepository<BankCard, Long> {
    //List<BankCard> findCardsByAccountId(String id);
}
