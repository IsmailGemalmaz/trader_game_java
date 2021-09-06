package com.example.myapplication.manager.api;

public class MultipartRequest{
/*
    private Gson mGson;
    private ApiMethod mApiMethod;
    private Class<T> mResponseClass;
    private Response.Listener mSuccessListener;
    private MultipartRequestParams mParams;
    private HttpEntity httpEntity;
    private Map<String, String> mHeaders;
    private ByteArrayOutputStream mBaos;
    private SecurityHelper mSecurityHelper;

    public MultipartRequest(Context context, ApiMethod apiMethod, BaseRequest request, Map<String, String> headers, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        super(apiMethod.getMethodType(), apiMethod.getUrl(), errorListener);
        mApiMethod = apiMethod;
        mResponseClass = (Class<T>) apiMethod.getResponseClass();
        mHeaders = headers != null ? new HashMap<>(headers) : new HashMap<String, String>();
        mSuccessListener = successListener;
        mParams = new MultipartRequestParams(request);
        mBaos = new ByteArrayOutputStream();
        mGson = new GsonBuilder().disableHtmlEscaping().create();
        mSecurityHelper = SecurityHelper.getInstance(context);
        if (mParams != null) {
            httpEntity = mParams.getEntity();
            mHeaders.put("Content-Type", httpEntity.getContentType().getValue());
            try {
                httpEntity.writeTo(mBaos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public byte[] getBody() {
        return mBaos.toByteArray();
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public String getBodyContentType() {
        return httpEntity.getContentType().getValue();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString = new String(response.data, ProjectSettings.API_DEFAULT_CHARSET);
            if (ProjectSettings.API_ENCRYPTION_ENABLED) {
                responseString = mSecurityHelper.decrypt(responseString);
            }
            L.e("API response received: " + mApiMethod + " " + responseString);
            T result = mGson.fromJson(responseString, mResponseClass);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mSuccessListener.onResponse(response);
    }*/
}
