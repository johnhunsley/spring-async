package com.hunsley.async.aggregator;

import com.hunsley.async.Account;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *  A consolidated view of a simple representation of a bank account which consists of an account type
 *  and a balance represented as a Double.
 * </p>
 * @author jphunsley@gmail.com
 */
public class ConsolidatedAccount implements Serializable {
    private static final long serialVersionUID = 42L;

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

    public void addAll(Collection<Account> col) {
        if(accounts == null) accounts = new HashSet<>();

        accounts.addAll(col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsolidatedAccount that = (ConsolidatedAccount) o;
        return Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts);
    }


}
