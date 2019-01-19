package com.hunsley.async.aggregator;

import java.util.Map;

/**
 * <p>
 *  A consolidated view of a simple representation of a bank account which consists of an account type
 *  and a balance represented as a Double.
 * </p>
 * @author jphunsley@gmail.com
 */
public class ConsolidatedAccount {
    private Map<String, Double> accounts;

    public Map<String, Double> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Double> accounts) {
        this.accounts = accounts;
    }
}
