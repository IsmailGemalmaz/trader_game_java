package com.example.myapplication.manager.api;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.ProjectSettings;
import com.example.myapplication.helper.SecurityHelper;
import com.example.myapplication.model.entity.CryptoFavoritesStcok;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.response.GetCryptoFavoritesResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;
import com.example.myapplication.utility.L;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BodyRequest<T> extends Request<T> {

    private Gson mGson;
    private ApiMethod mApiMethod;
    private Class<T> mResponseClass;
    private String mBody;
    private Map<String, String> mHeaders;
    private Response.Listener<T> mSuccessListener;
    private SecurityHelper mSecurityHelper;

    public BodyRequest(Context context, ApiMethod apiMethod, Object body, Map<String, String> headers, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        super(apiMethod.getMethodType(), apiMethod.getUrl(), errorListener);
        mApiMethod = apiMethod;
        mResponseClass = (Class<T>) apiMethod.getResponseClass();
        mHeaders = headers != null ? new HashMap<>(headers) : new HashMap<String, String>();
        mHeaders.put("Content-Type", apiMethod.getContentType().getTypeName());
        mSuccessListener = successListener;
        mGson = new GsonBuilder().disableHtmlEscaping().create();
        mBody = mGson.toJson(body);
        //mSecurityHelper = SecurityHelper.getInstance(context);
        //if (ProjectSettings.API_ENCRYPTION_ENABLED) {
          //  mBody = mSecurityHelper.encrypt(mBody);
       // }
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
    public byte[] getBody() {
        try {
            return mBody == null ? null : mBody.getBytes(ProjectSettings.API_DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mSuccessListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result;
            String responseString = new String(response.data, ProjectSettings.API_DEFAULT_CHARSET);
            L.e("API response recapimaeived: " + mApiMethod + " " + responseString);
            if (mResponseClass== GetCryptoFavoritesResponse.class){
                Type type=new TypeToken<List<CryptoFavoritesStcok>>(){}.getType();
                List<CryptoFavoritesStcok> stocks=mGson.fromJson(responseString,type);
                GetCryptoFavoritesResponse getCryptoStockResponse=new GetCryptoFavoritesResponse();
                getCryptoStockResponse.setStocks(stocks);
                result=(T)getCryptoStockResponse;
            }else{
                result = mGson.fromJson(responseString, mResponseClass);
            }
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }
}
