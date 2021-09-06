package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.model.response.BaseResponse;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.siginInBtn)
    Button siginInBtn;

    @BindView(R.id.signInActivityRegister)
    TextView registerBtn;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void prepareUI() {
        setWindowBackgroundImage(R.color.base_window_background);
    }

    @Override
    public void setListeners() {
        super.setListeners();
        siginInBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == siginInBtn){
            MainActivity.startWithNewTask(activity);
        }else if(view==registerBtn){
            RegisterActivity.start(activity);
        }
    }


    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
