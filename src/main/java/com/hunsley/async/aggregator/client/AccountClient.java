package com.hunsley.async.aggregator.client;

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
    public CompletableFuture<Map<String, Double>> getAccount(AccountType type) {
        StringBuffer builder = new StringBuffer(baseUrl).append("/").append(type.name());
        URI uri = URI.create(builder.toString());
        Map<String, Double> account = restTemplate.getForEntity(uri, Map.class).getBody();
        return CompletableFuture.completedFuture(account);
    }
}
