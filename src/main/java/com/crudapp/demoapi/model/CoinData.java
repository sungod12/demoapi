package com.crudapp.demoapi.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CoinData {

    private String name;

}
