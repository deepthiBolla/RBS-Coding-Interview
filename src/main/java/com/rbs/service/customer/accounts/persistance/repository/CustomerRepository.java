package com.rbs.service.customer.accounts.persistance.repository;

import com.rbs.service.customer.accounts.persistance.model.CardType;
import com.rbs.service.customer.accounts.persistance.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}
