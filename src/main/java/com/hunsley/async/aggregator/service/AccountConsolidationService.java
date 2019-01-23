package com.hunsley.async.aggregator.service;

import com.hunsley.async.Account;
import com.hunsley.async.aggregator.ConsolidatedAccount;
import com.hunsley.async.aggregator.client.AccountClient;
import com.hunsley.async.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 *     A service which is responsible for aggregating Bank Account data sourced from  different {@link AccountType}
 *     resources accessed by the {@link AccountClient}. This service is request scoped: a new been is created
 *     in the in Spring context for every Http Request originating
 *     from the {@link com.hunsley.async.aggregator.controller.ConsolidatedAccountController}.
 * </p>
 *
 * @author jphunsley@gmail.com
 */
@Service
@Scope(value = "prototype")
public class AccountConsolidationService {

    @Autowired
    private AccountClient client;

    /**
     * <p>
     *      Consolidates the data accessed from resources defined by {@link AccountType}s
     * </p>
     * @return an instance of {@link ConsolidatedAccount} which contains the data sourced from the services, accessed by
     * the {@link AccountClient} as defined by the complete list of enumerated {@link AccountType}s
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public ConsolidatedAccount consolidateAccounts() throws ExecutionException, InterruptedException {
        Set<CompletableFuture> accounts = new HashSet<>();

        for(AccountType type : AccountType.values()) {
            CompletableFuture<List> futures = client.getAccounts(type);
            accounts.add(futures);
        }

        CompletableFuture.allOf(accounts.toArray(new CompletableFuture[AccountType.values().length])).join();

        return consolidateFutures(accounts);
    }

    /**
     * <p>
     *     Consolidates the data from the given completed {@link CompletableFuture} instances
     *     into a {@link ConsolidatedAccount} instance.
     * </p>
     * @param futures
     * @return {@link ConsolidatedAccount}
     */
    private ConsolidatedAccount consolidateFutures(Collection<CompletableFuture> futures) throws ExecutionException, InterruptedException {
        ConsolidatedAccount consolidatedAccount = new ConsolidatedAccount();

        for(CompletableFuture<List> future : futures) {
            consolidatedAccount.addAll(future.get());
        }

        return consolidatedAccount;
    }
}
