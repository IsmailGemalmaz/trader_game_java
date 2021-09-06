package com.example.myapplication.model;

import com.example.myapplication.manager.api.ApiResponse;

public class BaseResponse extends ApiResponse {
    // FIXME message ve success field'lari projeye gore degisebilir
    private String message;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return !success;
    }

    public void setError(boolean isError) {
        success = !isError;
    }
}
