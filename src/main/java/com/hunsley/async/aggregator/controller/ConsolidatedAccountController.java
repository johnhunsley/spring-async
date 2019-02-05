package com.hunsley.async.aggregator.controller;


import com.hunsley.async.aggregator.ConsolidatedAccount;
import com.hunsley.async.aggregator.service.AccountConsolidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("asyncbank/")
public class ConsolidatedAccountController {

    @Autowired
    private AccountConsolidationService accountConsolidationService;

    private Logger logger = LoggerFactory.getLogger(ConsolidatedAccountController.class);

    @RequestMapping(value = "accounts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ConsolidatedAccount> getConsolidatedAccount() throws ExecutionException, InterruptedException {
        logger.info("Getting accounts.....");
        return new ResponseEntity<>(accountConsolidationService.getConsolidateAccounts(), HttpStatus.OK);
    }
}
