package com.example.myapplication.manager.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.ProjectSettings;
import com.example.myapplication.model.request.BaseRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApiManager {

    private static ApiManager instance;
    private final int RETRY_COUNT = ProjectSettings.API_RETRY_COUNT;

    private Context mContext;
    private List<ApiListener> mApiListener;
    private RequestQueue  mRequestQueue;
    private Map<String,String> mHeaders;
    private List<Object> mErrorCounter;
    private Gson mGson;

    public ApiManager(Context context) {
        mContext = context;
        mGson = new GsonBuilder().disableHtmlEscaping().create();
        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        mRequestQueue.start();
        mErrorCounter = new ArrayList<>();
        mApiListener = new ArrayList<>();
        mHeaders = new HashMap<>();
        mHeaders.put("Content-Type", ContentType.APPLICATION_JSON.getTypeName());
    }

    public static  ApiManager getInstance(Context context){
        if(instance==null){
            instance=new ApiManager(context);
        }
        return instance;
    }

    public static ApiManager newInstance(Context context) {
        return new ApiManager(context);
    }


    public void addListener(ApiListener listener) {
        mApiListener.add(listener);
    }

    public void removeListener(ApiListener listener) {
        mApiListener.remove(listener);
    }

    public void cancelRequest(ApiMethod method) {
        mRequestQueue.cancelAll(method);
    }

    public void cancelAllRequest() {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public void setAuthorizationToken(String token) {
        addHeader(ProjectSettings.API_HEADER_AUTHORIZATION_TOKEN_KEY, token);
    }

    public String getAuthorizationToken() {
        return mHeaders.get(ProjectSettings.API_HEADER_AUTHORIZATION_TOKEN_KEY);
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void send(BaseRequest request) {
        clearErrorCounter(request);
        addRequestToQueue(request);
    }

    public void clearErrorCounter(Object object) {
        Iterator<Object> iterator = mErrorCounter.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (object instanceof BaseRequest) {
                if (obj.getClass() == object.getClass()) {
                    iterator.remove();
                }
            } else {
                if (obj == object) {
                    iterator.remove();
                }
            }
        }
    }

    public boolean canRetry(Object object) {
        int i = 0;
        for (Object obj : mErrorCounter) {
            if (object instanceof BaseRequest) {
                if (object.getClass() == obj.getClass()) {
                    i++;
                }
            } else {
                if (object == obj) {
                    i++;
                }
            }
        }
        return i < RETRY_COUNT;
    }

    private void addRequestToQueue(BaseRequest request) {
        RequestType requestType = request.getApiMethod().getRequestType();
        Request r = null;
        if (requestType == RequestType.PARAMETER) {
            r = prepareParameterRequest(request);
        } else if (requestType == RequestType.BODY) {
            r = prepareBodyRequest(request);
        } else if (requestType == RequestType.MULTIPART) {
           /* r = prepareMultipartRequest(request);*/
        } else if (requestType == RequestType.QUERY) {
            r = prepareQueryStringRequest(request);
        }
        if (r != null) {
            mRequestQueue.add(r);
        }
    }

    private <T extends BaseResponse> void handleSuccessResponse(ApiMethod method, BaseRequest request, T response) {
        response.setRequest(request);
        response.setApiMethod(method);
        if (response.isError()) {
            response.setApiErrorType(ApiErrorType.SERVER);
        }
        for (ApiListener listener : mApiListener) {
            listener.onApiResponseReceive(method, response, response.isSuccess());
        }
    }

    private void handleErrorResponse(ApiMethod method, BaseRequest request, VolleyError volleyError) {
        ApiErrorType errorType = ApiErrorType.valueof(volleyError);
        boolean isErrorTypeRetryable = errorType == ApiErrorType.NETWORK || errorType == ApiErrorType.NO_CONNECTİON || errorType == ApiErrorType.TIMEOUT;
        boolean canRetry = canRetry(request);
        if (isErrorTypeRetryable && canRetry) {
            if (request != null) {
                mErrorCounter.add(request);
            } else {
                mErrorCounter.add(method);
            }
            addRequestToQueue(request);
        } else {
            BaseResponse baseResponse = null;
            boolean isNetworkDataExist = volleyError != null && volleyError.networkResponse != null && volleyError.networkResponse.data != null;
            if (isNetworkDataExist) {
                String serverResponseString = new String(volleyError.networkResponse.data);
                try {
                    baseResponse = mGson.fromJson(serverResponseString, BaseResponse.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (baseResponse == null) {
                baseResponse = new BaseResponse();
            }
            baseResponse.setError(true);
            baseResponse.setRequest(request);
            baseResponse.setApiMethod(method);
            baseResponse.setApiErrorType(errorType);
            boolean isNetworkResponseExist = volleyError != null && volleyError.networkResponse != null;
            if (isNetworkResponseExist) {
                baseResponse.setStatusCode(volleyError.networkResponse.statusCode);
            }
            for (ApiListener listener : mApiListener) {
                listener.onApiResponseReceive(method, baseResponse, baseResponse.isSuccess());
            }
        }
    }

    private <T extends BaseResponse> Response.Listener<T> prepareNewSuccessListener(final ApiMethod method, final BaseRequest request) {
        return new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if (response != null) {
                    handleSuccessResponse(method, request, response);
                } else {
                    handleErrorResponse(method, request, null);
                }
            }
        };
    }

    private Response.ErrorListener prepareNewErrorListener(final ApiMethod method, final BaseRequest request) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleErrorResponse(method, request, error);
            }
        };
    }

    private <T extends BaseResponse> QueryStringRequest<T> prepareQueryStringRequest(BaseRequest request) {
        Response.Listener<T> successListener = prepareNewSuccessListener(request.getApiMethod(), request);
        Response.ErrorListener errorListener = prepareNewErrorListener(request.getApiMethod(), request);
        ApiMethod method = request.getApiMethod();
        QueryStringRequest queryStringRequest = new QueryStringRequest(method, request, mHeaders, successListener, errorListener);
        queryStringRequest.setTag(request.getApiMethod());
        queryStringRequest.setRetryPolicy(new DefaultRetryPolicy(ProjectSettings.API_TIMEOUT_DURATION, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return queryStringRequest;
    }

    private <T extends BaseResponse> ParameterRequest<T> prepareParameterRequest(BaseRequest request) {
        ApiMethod method = request.getApiMethod();
        Response.Listener<T> successListener = prepareNewSuccessListener(request.getApiMethod(), request);
        Response.ErrorListener errorListener = prepareNewErrorListener(request.getApiMethod(), request);
        ParameterRequest parameterRequest = new ParameterRequest(method, request, mHeaders, successListener, errorListener);
        parameterRequest.setTag(request.getApiMethod());
        parameterRequest.setRetryPolicy(new DefaultRetryPolicy(ProjectSettings.API_TIMEOUT_DURATION, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return parameterRequest;
    }

    private <T extends BaseResponse> BodyRequest<T> prepareBodyRequest(BaseRequest request) {
        ApiMethod method = request.getApiMethod();
        Response.Listener<T> successListener = prepareNewSuccessListener(request.getApiMethod(), request);
        Response.ErrorListener errorListener = prepareNewErrorListener(request.getApiMethod(), request);
        BodyRequest bodyRequest = new BodyRequest(mContext, method, request, mHeaders, successListener, errorListener);
        bodyRequest.setTag(request.getApiMethod());
        bodyRequest.setRetryPolicy(new DefaultRetryPolicy(ProjectSettings.API_TIMEOUT_DURATION, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return bodyRequest;
    }
/*
    private <T extends BaseResponse> MultipartRequest<T> prepareMultipartRequest(BaseRequest request) {
        ApiMethod method = request.getApiMethod();
        Response.Listener<T> successListener = prepareNewSuccessListener(request.getApiMethod(), request);
        Response.ErrorListener errorListener = prepareNewErrorListener(request.getApiMethod(), request);
        MultipartRequest multipartRequest = new MultipartRequest(mContext, method, request, mHeaders, successListener, errorListener);
        multipartRequest.setTag(request.getApiMethod());
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(ProjectSettings.API_TIMEOUT_DURATION, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return multipartRequest;
    }*/
}
