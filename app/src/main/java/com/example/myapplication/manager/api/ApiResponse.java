package com.example.myapplication.manager.api;

import android.content.Context;
import android.text.TextUtils;

import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.model.request.BaseRequest;

public abstract class ApiResponse  extends ApiEntity{
    private ApiMethod mApiMethod;
    private ApiErrorType mApiErrorType;
    private BaseRequest mRequest;
    private byte[] mByteData;
    private int mStatusCode;

    public abstract String getMessage();

    public abstract boolean isError();

    public ApiMethod getApiMethod() {
        return mApiMethod;
    }

    public void setApiMethod(ApiMethod apiMethod) {
        mApiMethod = apiMethod;
    }

    public ApiErrorType getApiErrorType() {
        return mApiErrorType;
    }

    public void setApiErrorType(ApiErrorType apiErrorType) {
        mApiErrorType = apiErrorType;
    }

    public BaseRequest getRequest() {
        return mRequest;
    }

    public void setRequest(BaseRequest request) {
        mRequest = request;
    }

    public byte[] getByteData() {
        return mByteData;
    }

    public void setByteData(byte[] byteData) {
        mByteData = byteData;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public String getErrorMessage(Context context) {
        String errorMessage;
        ApiErrorType errorType = getApiErrorType();
        if (errorType == ApiErrorType.SERVER && mApiMethod.isShowErrorResponseMessage()) {
            String responseMessage = getMessage();
            if (!TextUtils.isEmpty(responseMessage)) {
                errorMessage = responseMessage;
            } else {
                errorMessage = context.getString(ApiErrorType.SERVER.getErrorMessageResId());
            }
        } else {
            errorMessage = context.getString(errorType.getErrorMessageResId());
        }
        return errorMessage;
    }
}
