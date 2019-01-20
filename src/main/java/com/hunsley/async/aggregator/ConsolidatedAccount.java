package com.hunsley.async.aggregator;

import com.hunsley.async.Account;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  A consolidated view of a simple representation of a bank account which consists of an account type
 *  and a balance represented as a Double.
 * </p>
 * @author jphunsley@gmail.com
 */
public class ConsolidatedAccount {
    private Set<Account> accounts;

    public ConsolidatedAccount() {}

    public ConsolidatedAccount(Set<Account> accounts) {
        this.accounts = accounts;
    }


    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void add(Account account) {
        if(accounts == null) accounts = new HashSet<>();

        accounts.add(account);
    }
}
