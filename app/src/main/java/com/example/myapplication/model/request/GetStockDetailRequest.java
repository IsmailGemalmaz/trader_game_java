package com.example.myapplication.model.request;

public class GetStockDetailRequest extends BaseRequest {

    private String ids;
    private String key;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
