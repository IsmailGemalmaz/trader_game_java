package com.example.myapplication.model.response;

import com.example.myapplication.model.entity.CryptoStock;

import java.util.List;

public class GetCryptoStockResponse extends BaseResponse {

    private List<CryptoStock> stocks;

    public List<CryptoStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<CryptoStock> stocks) {
        this.stocks = stocks;
    }


}
