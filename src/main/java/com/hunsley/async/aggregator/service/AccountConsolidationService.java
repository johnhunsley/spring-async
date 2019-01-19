package com.hunsley.async.aggregator.service;

import com.hunsley.async.aggregator.ConsolidatedAccount;
import com.hunsley.async.aggregator.client.AccountClient;
import com.hunsley.async.aggregator.client.AccountType;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AccountConsolidationService {

    @Autowired
    private AccountClient client;

    public ConsolidatedAccount consolidateAccounts() throws ExecutionException, InterruptedException {

        CompletableFuture<Pair<String, Double>> current = client.getAccount(AccountType.CURRENT);
        CompletableFuture<Pair<String, Double>> isa = client.getAccount(AccountType.ISA);
        CompletableFuture<Pair<String, Double>> joint = client.getAccount(AccountType.JOINT);
        CompletableFuture<Pair<String, Double>> saving = client.getAccount(AccountType.SAVINGS);

        CompletableFuture.allOf(current, isa, joint, saving).join();

        ConsolidatedAccount consolidatedAccount = new ConsolidatedAccount();
        consolidatedAccount.put(current.get().getKey(), current.get().getValue());
        consolidatedAccount.put(isa.get().getKey(), isa.get().getValue());
        consolidatedAccount.put(joint.get().getKey(), joint.get().getValue());
        consolidatedAccount.put(saving.get().getKey(), saving.get().getValue());

        return consolidatedAccount;


    }
}
