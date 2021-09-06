package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.model.response.BaseResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btnWelcomeSignIn)
    Button btnWelcomeSiginIn;

    @BindView(R.id.textRegister)
    TextView textRegister;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void prepareUI() {
        setWindowBackgroundImage(R.color.base_window_background);

    }


    @Override
    public void setListeners() {
        super.setListeners();
        btnWelcomeSiginIn.setOnClickListener(this);
        textRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v==btnWelcomeSiginIn){
            LoginActivity.start(activity);
        }else if(v==textRegister){
            RegisterActivity.start(activity);
        }
  }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
