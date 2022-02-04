package com.example.myapplication.constant;

import com.android.volley.Request;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.manager.api.ContentType;
import com.example.myapplication.manager.api.RequestType;
import com.example.myapplication.manager.api.ResponseFormat;

public  abstract class ProjectSettings {

    public static final boolean IS_DEBUG = false && BuildConfig.DEBUG;
    public static final String LOG_TAG = "Trader Games";
    public static final boolean IS_PROD = true || !BuildConfig.DEBUG;

    /**
     * Request'lerin default timeout süresi.
     */
    public static final int API_TIMEOUT_DURATION = 30000;

    /**
     * API çağrıları network, no_connection ve timeout error'larında bu değere göre retry yapar.
     */
    public static final int API_RETRY_COUNT = 1;

    public static final String API_HEADER_AUTHORIZATION_TOKEN_KEY = "authorization";

    /**
     * BaseUtilityActivity'de klavyenin açılıp kapanmasını dinleme özelliği var.
     * Bu değişken true yapılırsa bu özellik aktifleşir ve klavye durumunda değişiklik olduğunda Activity'lerin
     * onShowKeyboard, onHideKeyboard metodları tetiklenir.
     */
    public static final boolean ACTIVITY_KEYBOARD_LISTENER_ENABLED = false;

    /**
     * ProgressDialog'un onBackPressed'da ve onTouchOutside'da cancel edilebilmesini belirler.
     */
    public static final boolean PROGRESS_DIALOG_CANCELABLE_ENABLED = false;

    /**
     * API çağrılarında progress görünürlüğünün varsayılan değerini belirler.
     * Çoğu API çağrısında progress gösterilecekse true set'lenir.
     */
    public static final boolean API_DEFAULT_REQUEST_SHOWS_PROGRESS = false;
    public static final RequestType API_DEFAULT_REQUEST_TYPE = RequestType.BODY;
    public static final boolean API_DEFAULT_SHOW_ERROR_RESPONSE_MESSAGE = true;
    public static final int API_DEFAULT_METHOD_TYPE = Request.Method.POST;
    public static final ResponseFormat API_DEFAULT_RESPONSE_FORMAT = ResponseFormat.JSON;
    public static final ContentType API_DEFAULT_CONTENT_TYPE = ContentType.APPLICATION_JSON;
    public static final String API_DEFAULT_CHARSET = "UTF-8";
    public static final boolean API_ENCRYPTION_ENABLED = true;



    public static final String API_BASE_URL_TEST;
    public static final String API_BASE_URL_PROD;
    public static final String CRYPTO_TRANSFORMATION_TYPE;
    public static final String CRYPTO_ALGORITHM;
    public static final String CRYPTO_CERTIFICATE_FACTORY_TYPE;
    public static final String CRYPTO_CERTIFICATE_ALGORITHM;

    static {
        API_BASE_URL_TEST = "";
        API_BASE_URL_PROD = "";
        CRYPTO_TRANSFORMATION_TYPE = "";
        CRYPTO_ALGORITHM = "";
        CRYPTO_CERTIFICATE_FACTORY_TYPE ="";
        CRYPTO_CERTIFICATE_ALGORITHM = "";
    }

    public static native String[] getSecrets();

    public static String getApiBaseUrl() {
        return IS_PROD ? API_BASE_URL_PROD : API_BASE_URL_TEST;
    }

    /**
     * Verilerin SharedPreference'a şifrelenerek kaydedilmesini belirler.
     */
    public static final boolean PREFERENCE_ENCRYPTION_ENABLED = true;
}
