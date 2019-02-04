package com.hunsley.async.aggregator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hunsley.async.Account;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 *  A consolidated view of simple representations of bank accounts which are provided in a Collection
 *  of completed {@link CompletableFuture} instances which contain {@link Account}s. This view of consolidated
 *  {@link Account} instances also contains the time taken to complete all the tasks to fetch the accounts purely
 *  for demo of doing the underlying tasks asynchronously
 * </p>
 * @author jphunsley@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ConsolidatedAccount implements Serializable {
    private static final long serialVersionUID = 42L;

    /**
     * The time taken to fetch the accounts which are consolidated.
     * Used to demo the reduced time taken in asynchronous operations when
     * consolidating the account information from remote sources.
     */
    private final long consolidationTime;

    private final Set<Account> accounts;

    /**
     *
     * @param consolidationTime
     * @param futures
     * @throws ExecutionException
     * @throws InterruptedException if any of the give {@link CompletableFuture} instance are not done.
     */
    public ConsolidatedAccount(
                final long consolidationTime,
                final Collection<CompletableFuture<List<Account>>> futures)
            throws ExecutionException, InterruptedException {
        this.consolidationTime = consolidationTime;
        this.accounts = new HashSet<>();

        for(CompletableFuture<List<Account>> future : futures) {

            if(!future.isDone()) throw new InterruptedException(future.toString());

            accounts.addAll(future.get());
        }
    }

    public long getConsolidationTime() {
        return consolidationTime;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsolidatedAccount that = (ConsolidatedAccount) o;
        return consolidationTime == that.consolidationTime &&
                accounts.equals(that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consolidationTime, accounts);
    }
}
