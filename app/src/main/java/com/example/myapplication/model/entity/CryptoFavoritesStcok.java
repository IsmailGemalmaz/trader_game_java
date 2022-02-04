package com.example.myapplication.model.entity;

import java.util.Currency;

public class CryptoFavoritesStcok  extends BaseEntity{

    private String currency;
    private String price;
    private  String market_cap;
    private String name;

    public String getPrice() {
        return price;
    }

    public String getMarket_cap() {
        return market_cap;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }
}
