package com.example.myapplication.model.response;

import com.example.myapplication.model.entity.UserEntity;

import java.util.List;

public class LoginUserResponse extends BaseResponse {

   private UserEntity user;
   private boolean err;
    private String token;

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
