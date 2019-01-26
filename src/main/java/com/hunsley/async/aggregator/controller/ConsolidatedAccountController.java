package com.hunsley.async.aggregator.controller;


import com.hunsley.async.aggregator.ConsolidatedAccount;
import com.hunsley.async.aggregator.service.AccountConsolidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("asyncbank/")
public class ConsolidatedAccountController {

    @Autowired
    private AccountConsolidationService accountConsolidationService;

    @RequestMapping(value = "accounts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ConsolidatedAccount> getConsolidatedAccount() throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(accountConsolidationService.consolidateAccounts(), HttpStatus.OK);
    }

    @RequestMapping(value = "accounts", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ConsolidatedAccount> saveConsolidatedAccount(@RequestBody ConsolidatedAccount consolidatedAccount) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(accountConsolidationService.saveConsolidatedAccounts(consolidatedAccount), HttpStatus.ACCEPTED);
    }
}
