package com.rbs.service.customer.accounts.persistance.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryTest {

    private static final String TEST_ACCOUNT_NUMBER = "TESTACC1";

    @Autowired
    private AccountRepository accountRepository;

    //TODO implement this
    @Test
    public void testFindAccountByIdAndActiveTrue() {

    }

    @Test
    void injectedComponentsAreNotNull(){
        assertNotNull(accountRepository);
    }
}
