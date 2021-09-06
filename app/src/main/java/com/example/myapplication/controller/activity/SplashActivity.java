package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.model.response.BaseResponse;

public class SplashActivity  extends BaseActivity {

    Activity activity;

    public static final String EXTRA_IS_OPENED_BY_NOTIFICATION = getNewExtraId();
    public static final String EXTRA_NOTIFICATION_TYPE = getNewExtraId();

    private static final int START_NEXT_ACTIVITY_DELAY = 1000;

    private int mMaxInitialStepCount = 2;
    private int mCurrentStepCount;
    private boolean mIsOpenedByNotification;
    private int mNotificationType;



    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void prepareUI() {
        setWindowBackgroundImage(R.color.base_window_background);



    }

    @Override
    public void assignObjects() {
        super.assignObjects();

    }


    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
