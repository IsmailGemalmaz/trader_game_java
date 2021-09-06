package com.example.myapplication.model.response;

import com.example.myapplication.model.entity.CryptoStock;

import java.util.List;

public class GetStockDetailResponse extends BaseResponse {

    private List <CryptoStock> stock;


    private  CryptoStock cryptoStock;


    public CryptoStock getCryptoStock() {
        return cryptoStock;
    }

    public void setCryptoStock(CryptoStock cryptoStock) {
        this.cryptoStock = cryptoStock;
    }

    public List<CryptoStock> getStock() {
        return stock;
    }

    public void setStock(List<CryptoStock> stock) {
        this.stock = stock;
    }
}
