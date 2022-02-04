package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.model.request.LoginUserRequest;
import com.example.myapplication.model.request.RegisterUserRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.LoginUserResponse;
import com.example.myapplication.utility.L;
import com.example.myapplication.utility.ValidationUtil;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public class LoginActivity extends BaseActivity  implements View.OnClickListener {

    @BindView(R.id.signInEmail)
    TextInputLayout signInEmail;
    @BindView(R.id.signInPassword)
    TextInputLayout signInPassword;
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
            if(isValid()){
                sendLoginUserRequest();
            }
        }else if(view==registerBtn){
            RegisterActivity.start(activity);
        }
    }

    private void sendLoginUserRequest(){
        LoginUserRequest request=new LoginUserRequest();
        request.setEposta(signInEmail.getEditText().getText().toString());
        request.setPassword(signInPassword.getEditText().getText().toString());
        sendRequest(request);
    }

    private void handleLoginResponse(LoginUserResponse loginUserResponse){
        String token=loginUserResponse.getToken();
        getApiManager().setAuthorizationToken(token);
        getPreference().setToken(token);
        getPreference().setUser(loginUserResponse.getUser());
        L.d(loginUserResponse.isErr());
        if(loginUserResponse.isErr()==true){
            signInEmail.setError(getString(R.string.validation_user));
        }else {
            MainActivity.startWithNewTask(activity);
        }

    }


    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {
        if(method==ApiMethod.LOGIN_USER){
            LoginUserResponse loginUserResponse=(LoginUserResponse) response;
            getPreference().setToken(loginUserResponse.getToken());
            handleLoginResponse(loginUserResponse);
        }
    }

    private boolean isValid() {
        boolean isValid = true;
        String email = signInEmail.getEditText().getText().toString().trim();
        String password = signInPassword.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            signInEmail.setError(getString(R.string.validation_default));
            isValid = false;
        } else if (!ValidationUtil.email(email)) {
            signInEmail.setError(getString(R.string.validation_email));
            isValid = false;
        } else if (TextUtils.isEmpty(password)) {
            signInPassword.setError(getString(R.string.validation_default));
            isValid = false;
        }
        return isValid;
    }
}
