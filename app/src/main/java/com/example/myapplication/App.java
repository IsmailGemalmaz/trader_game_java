package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.EventListener;
import com.example.myapplication.helper.PreferenceHelper;
import com.example.myapplication.manager.api.ApiListener;
import com.example.myapplication.manager.api.ApiManager;
import com.example.myapplication.model.entity.UserEntity;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetConfigResponse;
import com.example.myapplication.model.response.LoginUserResponse;

public class App extends Application implements ApiListener, EventListener {


    private GetConfigResponse mGetConfigResponse;
    private static App mInstance;
    private ApiManager mApiManager;
    private  UserEntity mUser;
    private PreferenceHelper mPreferenceHelper;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mPreferenceHelper=new PreferenceHelper(this);
        mApiManager=ApiManager.getInstance(this);
    }

    public UserEntity getUser() {
        return mUser;
    }

    @Override
    public void onEventReceive(int event, Object... data) {

    }

    public GetConfigResponse getGetConfigResponse() {
        return mGetConfigResponse;
    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {
        if(method==ApiMethod.LOGIN_USER){
            LoginUserResponse login=(LoginUserResponse) response;
            String token=login.getToken();
            mApiManager.setAuthorizationToken(token);
            mPreferenceHelper.setToken(token);
            mUser=login.getUser();


        }
    }
}


