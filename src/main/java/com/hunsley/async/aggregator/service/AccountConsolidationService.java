package com.hunsley.async.aggregator.service;

import com.hunsley.async.Account;
import com.hunsley.async.aggregator.ConsolidatedAccount;
import com.hunsley.async.aggregator.client.AccountClient;
import com.hunsley.async.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountConsolidationService {

    @Autowired
    private AccountClient client;

    /**
     * <p>
     *    Consolidates the data accessed from resources defined by {@link AccountType}s
     * </p>
     * @return an instance of {@link ConsolidatedAccount} which contains the data sourced from the services, accessed by
     * the {@link AccountClient} as defined by the complete list of enumerated {@link AccountType}s
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public ConsolidatedAccount consolidateAccounts() throws ExecutionException, InterruptedException {
        Set<CompletableFuture<List<Account>>> accounts = new HashSet<>();

        final long start = System.currentTimeMillis();

        for(AccountType type : AccountType.values()) {
            CompletableFuture<List<Account>> futures = client.getAccounts(type);
            accounts.add(futures);
        }

        CompletableFuture.allOf(accounts.toArray(new CompletableFuture[AccountType.values().length])).join();
        ConsolidatedAccount consolidatedAccount = new ConsolidatedAccount(System.currentTimeMillis() - start);

        for(CompletableFuture<List<Account>> future : accounts) {
            consolidatedAccount.addAll(future.get());
        }

        return consolidatedAccount;
    }


    /**
     * <p>
     *     Iterates the {@link Set} of {@link Account} instances in the given {@link ConsolidatedAccount}
     *     and persists each in an asynchronous call to the back end service via the {@link AccountClient}
     *
     *     Transactions cannot traverse thread boundaries. So, this function is transactional and acts as an
     *     Orchestrator which awaits completion of each thread and individual transaction before registering
     *     as completed
     * </p>
     * @param consolidatedAccount
     * @return
     */
    @Transactional
    public ConsolidatedAccount saveConsolidatedAccounts(ConsolidatedAccount consolidatedAccount) throws ExecutionException, InterruptedException {
        Set<CompletableFuture<Account>> accounts = new HashSet<>();
        final long start = System.currentTimeMillis();

        for(Account account : consolidatedAccount.getAccounts()) {
            CompletableFuture<Account> future = client.saveAccount(account);
            accounts.add(future);
        }

        CompletableFuture.allOf(accounts.toArray(new CompletableFuture[accounts.size()-1])).join();
        ConsolidatedAccount returnable = new ConsolidatedAccount(System.currentTimeMillis() - start);

        for(CompletableFuture<Account> future : accounts) {
            returnable.add(future.get());
        }

        return returnable;
    }
}
