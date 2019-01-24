package com.hunsley.async.services.controller;

import com.hunsley.async.Account;
import com.hunsley.async.AccountType;
import javafx.util.Pair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *     A JPA {@link org.springframework.stereotype.Repository} which enables CRUD, paging and searching functions for
 *     {@link Account} instances, exposed as a REST API with Spring Data REST {@link RepositoryRestResource}
 * </p>
 * <p>
 *     The following examples of functions are exposed on the API
 *
 *     GET /accounts
 *     POST/PUT /account - HTTP Entity = {"accountType":"ISA", "balance":10.0 }
 *     GET /accounts/{accountId}
 *     GET /accounts/search/findByAccountType?type=ISA
 * </p>
 * @author jphunsley@gmail.com
 */
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    /**
     * <p>
     *     GET accounts/search/findByAccountType?type=TYPE
     *
     *     where TYPE is an enumerated {@link AccountType}
     * </p>
     * @param type
     * @return a list of {@link Account} search results
     */
    List<Account> findByAccountType(@Param("type") AccountType type);

}
