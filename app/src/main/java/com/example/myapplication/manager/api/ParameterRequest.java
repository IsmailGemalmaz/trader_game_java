package com.example.myapplication.manager.api;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.ProjectSettings;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.request.BaseRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;
import com.example.myapplication.model.response.GetStockDetailResponse;
import com.example.myapplication.utility.L;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterRequest<T> extends com.android.volley.toolbox.StringRequest {
    private Gson mGson;
    private ApiMethod mApiMethod;
    private Class<T> mResponseClass;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private Response.Listener mSuccessListener;
    private Response.ErrorListener mErrorListener;
    private ResponseFormat mResponseFormat;

    public ParameterRequest(ApiMethod apiMethod, BaseRequest request, Map<String, String> headers, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(apiMethod.getMethodType(), apiMethod.getUrl(), successListener, errorListener);
        mGson = new GsonBuilder().disableHtmlEscaping().create();
        mApiMethod = apiMethod;
        mResponseClass = (Class<T>) apiMethod.getResponseClass();
        mHeaders = headers != null ? new HashMap<>(headers) : new HashMap<String, String>();
        mHeaders.put("Content-Type", apiMethod.getContentType().getTypeName());
        mSuccessListener = successListener;
        mErrorListener = errorListener;
        mResponseFormat = apiMethod.getResponseFormat();
        mParams = new HashMap<>();
        prepareParams(request);
    }

    @Override
    public String getBodyContentType() {
        return mApiMethod.getContentType().getTypeName();
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected void deliverResponse(String response) {
        T result = mGson.fromJson(response, mResponseClass);
        mSuccessListener.onResponse(result);
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {

            String responseString = new String(response.data, ProjectSettings.API_DEFAULT_CHARSET);
            L.e("API response received: " + mApiMethod + " " + responseString);
            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    private void prepareParams(BaseRequest request) {
        try {
            Field[] allFields = request.getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(request);
                if (!key.equals("serialVersionUID")) {
                    if (value != null) {
                        mParams.put(key, value.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
