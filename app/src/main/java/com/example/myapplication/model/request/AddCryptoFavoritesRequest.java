package com.example.myapplication.model.request;

public class AddCryptoFavoritesRequest extends BaseRequest {

    public String currency;

    //private String price;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /*public void setPrice(String price) {
        price = price;
    }*/
}
