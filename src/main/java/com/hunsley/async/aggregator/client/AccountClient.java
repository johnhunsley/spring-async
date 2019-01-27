package com.hunsley.async.aggregator.client;

import com.hunsley.async.Account;
import com.hunsley.async.AccountType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
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
    public CompletableFuture<List<Account>> getAccounts(AccountType type) throws InterruptedException {
        URI uri = URI.create(baseUrl + "/search/findByAccountType?type=" + type.name());
        ResponseEntity<ServicesResponse<Account>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServicesResponse<Account>>(){});
        Thread.sleep(2000L);
        return CompletableFuture.completedFuture(response.getBody().getEmbeddedItems());
    }

}
