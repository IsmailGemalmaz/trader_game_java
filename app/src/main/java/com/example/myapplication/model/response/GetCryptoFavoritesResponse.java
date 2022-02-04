package com.example.myapplication.model.response;

import com.example.myapplication.model.entity.CryptoFavoritesStcok;
import com.example.myapplication.model.entity.CryptoStock;

import java.util.List;

public class GetCryptoFavoritesResponse extends BaseResponse {


    private List<CryptoFavoritesStcok> stocks;

    public List<CryptoFavoritesStcok> getCryptoStocks() {
        return stocks;
    }

    public void setStocks(List<CryptoFavoritesStcok> stocks) {
        this.stocks = stocks;
    }
}
