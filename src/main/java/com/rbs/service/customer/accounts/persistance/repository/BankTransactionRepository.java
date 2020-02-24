package com.rbs.service.customer.accounts.persistance.repository;

import com.rbs.service.customer.accounts.persistance.model.BankTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankTransactionRepository extends CrudRepository<BankTransaction, Long> {

    @Query(value = "SELECT * FROM bank_transaction bt where bt.from_account_id  = :account_id " +
            "OR bt.to_account_id  = :account_id order by transaction_date desc limit :limit", nativeQuery = true)
    List<BankTransaction> findAccountTransactions(@Param("account_id") String accountId, @Param("limit") int limit);

}
