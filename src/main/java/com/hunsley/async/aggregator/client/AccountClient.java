package com.hunsley.async.aggregator.client;

import com.hunsley.async.Account;
import com.hunsley.async.AccountType;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *     A wrapper for a {@link RestTemplate} implementation which makes a GET request to the URI mapped to
 *     the resource defined by the given enumerated {@link AccountType}
 * </p>
 * @author jphunsley@gmail.com
 */
@Service
@Scope(value = "prototype")
public class AccountClient {
    @Value("${async.services.base.url}")
    private String baseUrl;

    final RestTemplate restTemplate;

    public AccountClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<Account> getAccount(AccountType type) {
        URI uri = URI.create(baseUrl + "?type=" + type.name());
        Account account = restTemplate.getForEntity(uri, Account.class).getBody();
        return CompletableFuture.completedFuture(account);
    }
}
