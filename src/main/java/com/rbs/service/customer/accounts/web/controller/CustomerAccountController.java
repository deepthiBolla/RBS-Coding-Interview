package com.rbs.service.customer.accounts.web.controller;

import com.rbs.service.customer.accounts.application.CustomerAccountDelegator;
import com.rbs.service.customer.accounts.web.model.AccountVO;
import com.rbs.service.customer.accounts.web.model.CustomerTransactionVo;
import com.rbs.service.customer.accounts.web.model.DepositRequest;
import com.rbs.service.customer.accounts.web.model.WithDrawlRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static com.rbs.service.customer.accounts.web.controller.CustomerAccountController.REQUEST_MAPPING;

@RestController
@RequestMapping(value = REQUEST_MAPPING)
@Api(value = REQUEST_MAPPING, tags = "customerAccount")
public class CustomerAccountController {

    static final String REQUEST_MAPPING = "/accounts";
    private static final String CARD_TYPE = "cardType";
    private static final String ACCOUNT_NUMBER = "accountNumber";
    private static final String NUMBER_OF_TRANSACTIONS = "numberOfTransactions";


    private CustomerAccountDelegator customerAccountApplication;

    @Autowired
    public CustomerAccountController(CustomerAccountDelegator customerAccountApplication) {
        this.customerAccountApplication = customerAccountApplication;
    }

    @ApiOperation(response = BigDecimal.class, value = "Balance Enquiry", notes = "Get current balance for an account number")
    @GetMapping(value = "/" + "{" + ACCOUNT_NUMBER + "}" + "/balance")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal balanceEnquiry(@PathVariable String accountNumber,
                                     @RequestParam("customerId") String customerId) {
        return customerAccountApplication.getBalance(accountNumber, customerId);
    }

    @ApiOperation(response = AccountVO.class, value = "WithDrawl", notes = "WithDrawl of cash from a bank account")
    @PatchMapping(value = "/withDrawl")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountVO> withDrawl(@Valid @RequestBody WithDrawlRequest withDrawl) {
        AccountVO accountVO = customerAccountApplication.withDrawl(withDrawl);
        return ResponseEntity.ok().body(accountVO);
    }

    @ApiOperation(response = AccountVO.class, value = "Deposit Cash", notes = "deposit cash for a bank account")
    @PatchMapping(value = "/deposit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountVO> deposit(@Valid @RequestBody DepositRequest depositAction) {
        AccountVO accountVO = customerAccountApplication.deposit(depositAction);
        return ResponseEntity.ok().body(accountVO);
    }

    @ApiOperation(response = BigDecimal.class, value = "Interest Rate Enquiry", notes = "Get interest rate value for a credit/debit card associated with account")
    @GetMapping(value = "/" + "{" + ACCOUNT_NUMBER + "}" + "/" + "{" + CARD_TYPE + "}" + "/interestRate")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getInterestRate(@PathVariable String accountNumber,
                                      @PathVariable String cardType,
                                      @RequestParam("customerId") String customerId) {
        return customerAccountApplication.getInterestRate(accountNumber, cardType, customerId);
    }

    @ApiOperation(response = CustomerTransactionVo.class, responseContainer = "List", value = "Account Transactions", notes = "Get List of transactions for an account")
    @GetMapping(value = "/" + "{" + ACCOUNT_NUMBER + "}" + "/transactions" + "/" + "{" + NUMBER_OF_TRANSACTIONS + "}")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerTransactionVo> getTransactions(@PathVariable String accountNumber,
                                                       @PathVariable int numberOfTransactions,
                                                       @RequestParam("customerId") String customerId) {
        return customerAccountApplication.getTransactions(accountNumber, numberOfTransactions, customerId);
    }
}
