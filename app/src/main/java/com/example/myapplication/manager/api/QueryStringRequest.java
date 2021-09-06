package com.example.myapplication.manager.api;

import android.service.quickaccesswallet.GetWalletCardsResponse;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryStringRequest<T> extends Request<T> {
    private final String TAG = ProjectSettings.LOG_TAG;

    private Gson mGson;
    private String mUrl;
    private ApiMethod mApiMethod;
    private Class<T> mResponseClass;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private Response.Listener mSuccessListener;
    private ResponseFormat mResponseFormat;

    public QueryStringRequest(ApiMethod apiMethod, BaseRequest request, Map<String, String> headers, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(apiMethod.getMethodType(), apiMethod.getUrl(), errorListener);
        mResponseClass = (Class<T>) apiMethod.getResponseClass();
        mResponseFormat = apiMethod.getResponseFormat();
        mApiMethod = apiMethod;
        mHeaders = headers != null ? new HashMap<>(headers) : new HashMap<String, String>();
        mHeaders.put("Content-Type", apiMethod.getContentType().getTypeName());
        mSuccessListener = successListener;
        mGson = new GsonBuilder().disableHtmlEscaping().create();
        mParams = new HashMap<>();
        prepareParams(request);
        mUrl = prepareUrl(apiMethod.getUrl());
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    public String getBodyContentType() {
        return mApiMethod.getContentType().getTypeName();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mSuccessListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result;
            if (mResponseFormat == ResponseFormat.JSON) {
                String responseString = new String(response.data, ProjectSettings.API_DEFAULT_CHARSET);
                L.e("API response recapimaeived: " + mApiMethod + " " + responseString);
                if (mResponseClass== GetCryptoStockResponse.class){
                    Type type=new TypeToken<List<CryptoStock>>(){}.getType();
                    List<CryptoStock> stocks=mGson.fromJson(responseString,type);
                    GetCryptoStockResponse getCryptoStockResponse=new GetCryptoStockResponse();
                    getCryptoStockResponse.setStocks(stocks);
                    result=(T)getCryptoStockResponse;
                }else if(mResponseClass== GetStockDetailResponse.class){
                    Type type=new TypeToken<List<CryptoStock>>(){}.getType();
                    List<CryptoStock> stocks=mGson.fromJson(responseString,type);
                    GetStockDetailResponse getCryptoStockResponse=new GetStockDetailResponse();
                    getCryptoStockResponse.setStock(stocks);
                    result=(T)getCryptoStockResponse;
                }else{
                    result = mGson.fromJson(responseString, mResponseClass);
                }

            } else {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setByteData(response.data);
                result = (T) baseResponse;
            }
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    private String prepareUrl(String url) {
        String preparedUrl = url;
        if (mParams != null) {
            Map.Entry<String, String>[] entrySet = (Map.Entry<String, String>[]) mParams.entrySet().toArray(new Map.Entry[mParams.size()]);
            for (int i = 0; i < entrySet.length; i++) {
                Map.Entry<String, String> param = entrySet[i];
                try {
                    String key = URLEncoder.encode(param.getKey(), "utf-8");
                    String value = URLEncoder.encode(param.getValue(), "utf-8");
                    if (i == 0) {
                        preparedUrl += "?";
                    } else {
                        preparedUrl += "&";
                    }
                    preparedUrl += key + "=" + value;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return preparedUrl;
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
