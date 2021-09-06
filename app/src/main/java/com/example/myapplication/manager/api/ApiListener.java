package com.example.myapplication.manager.api;

import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.model.response.BaseResponse;

public interface ApiListener  {

    void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess);
}
