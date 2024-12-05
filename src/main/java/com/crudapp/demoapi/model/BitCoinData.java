package com.crudapp.demoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitCoinData {
    private String name;

    @JsonProperty(value = "quote")
    private Quote quote;
}
