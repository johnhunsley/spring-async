package com.hunsley.async.aggregator.client;

import javafx.util.Pair;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *
 * </p>
 * @author jphunsley@gmail.com
 */
@Service
public class AccountClient {

    private String baseUrl;

    final RestTemplate restTemplate;

    public AccountClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<Pair<String, Double>> getAccount(AccountType type) {
        URI uri = URI.create(baseUrl + "/" + type.name());
        Pair<String, Double> account = restTemplate.getForEntity(uri, Pair.class).getBody();
        return CompletableFuture.completedFuture(account);
    }
}
