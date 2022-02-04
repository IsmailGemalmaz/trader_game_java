package com.example.myapplication.model.response;

import com.example.myapplication.manager.api.ApiResponse;
import com.example.myapplication.utility.L;

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
        //return !success;
      return false;//TODO bunu kaldır dogru degıl suankı endpoıntten alamadıgımız ısscuucess degerınden dolayı boyle yaptık.
    }

    public void setError(boolean isError) {
        success = !isError;
    }
}

