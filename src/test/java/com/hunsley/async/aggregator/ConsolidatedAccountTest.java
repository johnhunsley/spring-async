package com.hunsley.async.aggregator;

import com.hunsley.async.Account;
import com.hunsley.async.AccountType;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * <p>
 *     Ensure the {@link ConsolidatedAccount} can only be created with {@link CompletableFuture} instances which
 *     have been completed
 * </p>
 */
public class ConsolidatedAccountTest {

    //happy test
    @Test
    public void testCreateConsolidatedAccount() {
        List<Account> accounts = new LinkedList<Account>() {{
            add(new Account(AccountType.CURRENT, 100.34));
            add(new Account(AccountType.ISA, 10010.40));
        }};

        Collection<CompletableFuture<List<Account>>> futures = new HashSet<CompletableFuture<List<Account>>>() {{}};

        CompletableFuture<List<Account>> future1 = new CompletableFuture<>();
        future1.complete(accounts);
        futures.add(future1);

        try {
            ConsolidatedAccount consolidatedAccount = new ConsolidatedAccount(100, futures);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            fail();
        }
    }

    //failure test
    @Test
    public void testFailCreateConsolidatedAccount() {
        List<Account> accounts = new LinkedList<Account>() {{
            add(new Account(AccountType.CURRENT, 100.34));
            add(new Account(AccountType.ISA, 10010.40));
        }};

        Collection<CompletableFuture<List<Account>>> futures = new HashSet<CompletableFuture<List<Account>>>() {{}};

        CompletableFuture<List<Account>> future1 = new CompletableFuture<>();
        future1.complete(accounts);
        futures.add(future1);
        //this future is not completed and should fail when we try to construct the Consolidated Account instance
        CompletableFuture<List<Account>> future2 = new CompletableFuture<>();
        assertFalse(future2.isDone());
        futures.add(future2);


        try {
            ConsolidatedAccount consolidatedAccount = new ConsolidatedAccount(100, futures);
            fail();

        } catch (ExecutionException | InterruptedException e) {
            //pass
        }
    }
}
