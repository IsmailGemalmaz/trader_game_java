package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.EventListener;
import com.example.myapplication.manager.api.ApiListener;
import com.example.myapplication.manager.api.ApiManager;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetConfigResponse;

public class App extends Application implements ApiListener, EventListener {


    private GetConfigResponse mGetConfigResponse;
    private static App mInstance;
    private ApiManager mApiManager;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mApiManager=ApiManager.getInstance(this);
    }

    @Override
    public void onEventReceive(int event, Object... data) {

    }

    public GetConfigResponse getGetConfigResponse() {
        return mGetConfigResponse;
    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
