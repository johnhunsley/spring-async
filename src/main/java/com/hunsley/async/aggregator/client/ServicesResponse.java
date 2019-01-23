package com.hunsley.async.aggregator.client;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hunsley.async.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 *     "_embedded": {
 *         "accounts": [
 *             {
 *                 "accountType": "ISA",
 *                 "balance": 4500,
 *                 "_links": {
 *                     "self": {
 *                         "href": "http://localhost:8080/accounts/1"
 *                     },
 *                     "account": {
 *                         "href": "http://localhost:8080/accounts/1"
 *                     }
 *                 }
 *             },
 *             {
 *                 "accountType": "ISA",
 *                 "balance": 43,
 *                 "_links": {
 *                     "self": {
 *                         "href": "http://localhost:8080/accounts/2"
 *                     },
 *                     "account": {
 *                         "href": "http://localhost:8080/accounts/2"
 *                     }
 *                 }
 *             }
 *         ]
 *     },
 *     "_links": {
 *         "self": {
 *             "href": "http://localhost:8080/accounts/search/findByAccountType?type=ISA"
 *         }
 *     }
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicesResponse<T> {
    @JsonProperty("_embedded")
    private Embedded<T> embedded;

    public Embedded<T> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public List<T> getEmbeddedItems() {
        return embedded.items;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Embedded<T>{
    @JsonProperty("accounts")
    List<T> items = new ArrayList<>();

    public Embedded(){

    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

