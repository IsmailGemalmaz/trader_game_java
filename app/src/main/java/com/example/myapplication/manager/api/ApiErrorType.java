package com.example.myapplication.manager.api;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public enum ApiErrorType {

    NO_CONNECTİON("İnternet Bağlantınızı Kontrol Edin", NoConnectionError.class),
    NETWORK("İnternet Bağlantınızı Kontrol Edin", NetworkError.class),
    AUTH_FAILURE("Yeniden Giriş Yapın", AuthFailureError .class),
    TIMEOUT("İstek zaman aşımna uğradı", TimeoutError .class),
    PARSE("yanlış veri formatı", ParseError.class),
    SERVER("sunucu hatası", ServerError.class, ClientError .class),
    UNKNOWN("Beklenmedik bir hata oluştu");

    private Class[] mVolleyErrorClasses;
    private String mErrorMessageResId;

    public static ApiErrorType valueof(VolleyError volleyError) {
        if (volleyError != null) {
            for (ApiErrorType type : values()) {
                for (Class<?> cls : type.getVolleyErrorClasses())
                    if (volleyError.getClass() == cls) {
                        return type;
                    }
            }
        }
        return UNKNOWN;
    }


    ApiErrorType(String errorMessageResId, Class... volleyErrorClasses) {
        mVolleyErrorClasses = volleyErrorClasses;
        mErrorMessageResId = errorMessageResId;
    }

    public Class[] getVolleyErrorClasses() {
        return mVolleyErrorClasses;
    }

    public int getErrorMessageResId() {
        return Integer.parseInt( mErrorMessageResId);
    }
}
