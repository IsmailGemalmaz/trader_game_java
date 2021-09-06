package com.example.myapplication.constant;

import com.android.volley.Request;
import com.example.myapplication.manager.api.ContentType;
import com.example.myapplication.manager.api.RequestType;
import com.example.myapplication.manager.api.ResponseFormat;
import com.example.myapplication.model.request.AddCryptoFavoritesRequest;
import com.example.myapplication.model.request.GetCryptoStockRequest;
import com.example.myapplication.model.request.GetStockDetailRequest;
import com.example.myapplication.model.response.AddCryptoFavoritesResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;
import com.example.myapplication.model.response.GetStockDetailResponse;

public enum ApiMethod {

    //https://api.nomics.com/v1/currencies/ticker?key=a8a3452e71305947867f9f04df8fd319
    // Https://api.nomics.com/v1/prices?key=a8a3452e71305947867f9f04df8fd319

    GET_CRYPTO_STOCK("https://api.nomics.com/v1/currencies/ticker", GetCryptoStockRequest.class, GetCryptoStockResponse.class, Request.Method.GET, RequestType.QUERY),
    GET_DETAIL_CRYPTO_STOCK("https://api.nomics.com/v1/currencies/ticker",GetStockDetailRequest.class, GetStockDetailResponse.class, Request.Method.GET, RequestType.QUERY),
    ADD_CRYPTO_TO_FAVORITES("http://192.168.1.49:3000/api/v1/crypto/favorites",AddCryptoFavoritesRequest.class, AddCryptoFavoritesResponse.class,Request.Method.POST,RequestType.BODY);

    private String mMethodName;
    private Class<?> mRequestClass;
    private RequestType mRequestType = ProjectSettings.API_DEFAULT_REQUEST_TYPE;
    private boolean mIsShowErrorResponseMessage = ProjectSettings.API_DEFAULT_SHOW_ERROR_RESPONSE_MESSAGE;
    private int mMethodType = ProjectSettings.API_DEFAULT_METHOD_TYPE;
    private Class<?> mResponseClass;
    private ResponseFormat mResponseFormat = ProjectSettings.API_DEFAULT_RESPONSE_FORMAT;
    private ContentType mContentType = ProjectSettings.API_DEFAULT_CONTENT_TYPE;

    public Class<?> getRequestClass() {
        return mRequestClass;
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public boolean isShowErrorResponseMessage() {
        return mIsShowErrorResponseMessage;
    }

    public int getMethodType() {
        return mMethodType;
    }

    public Class<?> getResponseClass() {
        return mResponseClass;
    }

    public String getUrl() {
        if (mMethodName.startsWith("http")) {
            return mMethodName;
        }
        String baseUrl = ProjectSettings.getApiBaseUrl();
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        return baseUrl + mMethodName;
    }

    public ResponseFormat getResponseFormat() {
        return mResponseFormat;
    }

    public ContentType getContentType() {
        return mContentType;
    }

    ApiMethod(String methodName, Class<?> requestClass, Class<?> responseClass) {
        mMethodName = methodName;
        mRequestClass = requestClass;
        mResponseClass = responseClass;
    }

    ApiMethod(String methodName, Class<?> requestClass, Class<?> responseClass, int methodType) {
        mMethodName = methodName;
        mRequestClass = requestClass;
        mResponseClass = responseClass;
        mMethodType = methodType;
    }

    ApiMethod(String methodName, Class<?> requestClass, Class<?> responseClass, int methodType, RequestType requestType) {
        mMethodName = methodName;
        mRequestClass = requestClass;
        mResponseClass = responseClass;
        mMethodType = methodType;
        mRequestType = requestType;
    }


    ApiMethod(String methodName, Class<?> requestClass, Class<?> responseClass, ContentType contentType, RequestType requestType) {
        mMethodName = methodName;
        mRequestClass = requestClass;
        mResponseClass = responseClass;
        mContentType = contentType;
        mRequestType = requestType;
    }

    ApiMethod(String methodName, Class<?> requestClass, Class<?> responseClass, int methodType, RequestType requestType, boolean isShowErrorResponseMessage) {
        mMethodName = methodName;
        mRequestClass = requestClass;
        mResponseClass = responseClass;
        mMethodType = methodType;
        mRequestType = requestType;
        mIsShowErrorResponseMessage = isShowErrorResponseMessage;
    }

    ApiMethod(String methodName, Class<?> requestClass, Class<?> responseClass, int methodType, RequestType requestType, int errorResponseMessageDuration) {
        mMethodName = methodName;
        mRequestClass = requestClass;
        mResponseClass = responseClass;
        mMethodType = methodType;
        mRequestType = requestType;

    }

    ApiMethod(String methodName, Class<?> requestClass, Class<?> responseClass, boolean isShowErrorResponseMessage, boolean isShowsMessageByToast) {
        mMethodName = methodName;
        mRequestClass = requestClass;
        mResponseClass = responseClass;
        mIsShowErrorResponseMessage = isShowErrorResponseMessage;

    }
}
