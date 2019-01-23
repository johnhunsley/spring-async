package com.hunsley.async;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * <p>
 *     A simple representation of a bank account of an enumerated {@link AccountType}
 *     which has an ID, generated by the persistent store, and a balance
 *
 *     example:
 *
 * </p>
 * @author johnhunsley
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private Double balance;

    public Account() {}

    public Account(AccountType accountType, Double balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                accountType == account.accountType &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountType, balance);
    }
}
