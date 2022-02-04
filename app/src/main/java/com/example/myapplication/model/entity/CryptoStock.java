package com.example.myapplication.model.entity;

import com.example.myapplication.manager.api.ApiEntity;

public class CryptoStock extends BaseEntity {

    private String currency;
    private Double price;
    private  String market_cap;
    private String name;
    private  String id;
    private String circulating_supply;
    private String max_supply;
    private String market_cap_dominance;
    private String rank;
    private String high;
    private String high_timestamp;




    public String getCirculating_supply() {
        return circulating_supply;
    }

    public String getMax_supply() {
        return max_supply;
    }

    public String getMarket_cap_dominance() {
        return market_cap_dominance;
    }

    public String getRank() {
        return rank;
    }

    public String getHigh() {
        return high;
    }

    public String getHigh_timestamp() {
        return high_timestamp;
    }

    public CryptoStockValue getStock() {
        return stock;
    }

    private CryptoStockValue stock;

    public CryptoStockValue getStockValue() {
        return stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMarket_cap() {
        return market_cap;
    }

    public String getCurrency() {
        return currency;
    }

    public double getPrice() {
        return price;
    }
}
