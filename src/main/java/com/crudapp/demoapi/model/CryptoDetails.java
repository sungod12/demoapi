package com.crudapp.demoapi.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoDetails {

    private Map<String,CoinData> data;

    @JsonAnySetter
    public void setBitCoinData(Map<String,CoinData> bitCoinDataMap){
        this.data=bitCoinDataMap;
    }

}
