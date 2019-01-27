package com.hunsley.async.aggregator.client;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of the JSON response from the Account REST Repository
 * {@link com.hunsley.async.services.controller.AccountRepository}
 *
 * Both classes are simple wrapper objects around an array of {@link com.hunsley.async.Account} types.
 *
 * {
 *     "_embedded": {
 *         "accounts": []
 *     }
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicesResponse<T> implements Serializable {
    @JsonProperty("_embedded")
    private Embedded<T> embedded;

    public Embedded<T> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public List<T> getEmbeddedItems() {
        if(embedded == null) embedded = new Embedded<>();
        return embedded.items;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Embedded<T> implements Serializable {
    @JsonProperty("accounts")
    List<T> items = new ArrayList<>();

    public Embedded() {}

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

