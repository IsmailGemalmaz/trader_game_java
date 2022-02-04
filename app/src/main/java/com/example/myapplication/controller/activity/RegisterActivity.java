package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.model.request.RegisterUserRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.view.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.registerBtn)
    Button registerBtn;
    @BindView(R.id.registerFirstName)
    TextInputLayout registerFirstName;
    @BindView(R.id.registerLastName)
    TextInputLayout registerLastName;
    @BindView(R.id.registerEmail)
    TextInputLayout registerEmail;
    @BindView(R.id.registerPassword)
    TextInputLayout registerPassword;
    @BindView(R.id.registerAgainPassword)
    TextInputLayout registerAgainPassword;




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
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==registerBtn){
            if(isValid()){
                sendAddUserRequest();
                Toast.info(context,"Kullanıcı başarıyla oluşturuldu ");
            }

        }
    }

    private boolean isValid(){
        String name = registerFirstName.getEditText().getText().toString().trim();
        String surname = registerLastName.getEditText().getText().toString().trim();
        String email = registerEmail.getEditText().getText().toString().trim();
        String password = registerPassword.getEditText().getText().toString().trim();
        String passwordAgain = registerAgainPassword.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            registerFirstName.setError(getString(R.string.validation_default));
            return false;
        } else if (name.length() < 3) {
            registerFirstName.setError(getString(R.string.validation_name_min_character));
            return false;
        } else if (TextUtils.isEmpty(surname)) {
            registerLastName.setError(getString(R.string.validation_default));
            return false;
        } else if (TextUtils.isEmpty(email)) {
            registerEmail.setError(getString(R.string.validation_default));
            return false;
        }   else if (password.length() < 6) {
            registerPassword.setError(getString(R.string.validation_password_min_character));
            return false;
        } else if (!password.equals(passwordAgain)) {
            registerAgainPassword.setError(getString(R.string.validation_password_again_incompatible));
            return false;
        }
        return true;
    }

    private void sendAddUserRequest(){
        RegisterUserRequest request=new RegisterUserRequest();
       request.setEposta(registerEmail.getEditText().getText().toString());
        request.setPassword(registerPassword.getEditText().getText().toString());
        request.setFirstName(registerFirstName.getEditText().getText().toString());
        request.setLastName( registerLastName.getEditText().getText().toString());
        sendRequest(request);
    }




    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
