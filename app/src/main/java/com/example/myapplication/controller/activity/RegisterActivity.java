package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.model.response.BaseResponse;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.registerBtn) Button registerBtn;



    public static void start(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void prepareUI() {
        setWindowBackgroundImage(R.color.base_window_background);
    }

    @Override
    public void setListeners() {
        super.setListeners();

    }

    @Override
    public void onClick(View v) {
        if(v==registerBtn){
            WelcomeActivity.start(activity);
        }
    }


    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
