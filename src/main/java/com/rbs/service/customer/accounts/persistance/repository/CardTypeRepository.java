package com.rbs.service.customer.accounts.persistance.repository;

import com.rbs.service.customer.accounts.persistance.model.CardType;
import org.springframework.data.repository.CrudRepository;

public interface CardTypeRepository extends CrudRepository<CardType, String> {

}
