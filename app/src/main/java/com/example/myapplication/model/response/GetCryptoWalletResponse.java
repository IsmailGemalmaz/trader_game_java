package com.example.myapplication.model.response;

import com.example.myapplication.model.entity.CryptoStock;

import java.util.List;

public class GetCryptoWalletResponse extends BaseResponse {

    private List<CryptoStock> wallet;

    public List<CryptoStock> getWallet() {
        return wallet;
    }

    public void setWallet(List<CryptoStock> wallet) {
        this.wallet = wallet;
    }
}
