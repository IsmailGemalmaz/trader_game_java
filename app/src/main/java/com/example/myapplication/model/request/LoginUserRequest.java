package com.example.myapplication.model.request;

public class LoginUserRequest extends  BaseRequest {

    private String password;
    private String eposta;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }
}
