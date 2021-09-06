package com.example.myapplication.controller.base;

import android.os.Build;

import androidx.annotation.CallSuper;
import androidx.annotation.RequiresApi;

import com.example.myapplication.App;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.ProjectSettings;
import com.example.myapplication.manager.api.ApiListener;
import com.example.myapplication.manager.api.ApiManager;
import com.example.myapplication.model.request.BaseRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.utility.L;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseApiActivity extends BaseUtilityActivity implements ApiListener{

    //region class variables
    private ApiManager mApiManager;
    private List<ApiMethod> mActiveApiMethods;
    private List<ApiListener> mApiListeners;
    private List<BaseRequest> mFailedRequests;
    private Snackbar mErrorSnackbar;
    //endregion

    //region callback & abstract methods
    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse baseResponse, boolean isSuccess) {}
    //endregion

    //region lifecycle methods
    @CallSuper
    @Override
    public void onCreated() {
        super.onCreated();
        mApiManager = ApiManager.getInstance(getApplicationContext());
        mApiManager.addListener(mApiListener);
        mApiListeners = new ArrayList<>();
        mActiveApiMethods = new ArrayList<>();
        mFailedRequests = new ArrayList<>();
    }

    @CallSuper
    @Override
    public void onDestroyed() {
        super.onDestroyed();
        mApiManager.removeListener(mApiListener);
    }
    //endregion

    //region public methods
    public App getApp() {
        return (App) getApplication();
    }


    public final ApiManager getApiManager() {
        return mApiManager;
    }

    public final void addApiListener(ApiListener listener) {
        mApiListeners.add(listener);
    }

    public final void removeApiListener(ApiListener listener) {
        mApiListeners.remove(listener);
    }

    public final void sendRequest(BaseRequest request) {
        sendRequest(request, ProjectSettings.API_DEFAULT_REQUEST_SHOWS_PROGRESS);
    }

    public final void sendRequest(BaseRequest request, boolean isShowProgressAllowed) {
        L.e("API request sending: " + request.getApiMethod() + " : " + request);
        //dismissErrorSnackbar();
        mFailedRequests.clear();
        request.setShowProgressAllowed(isShowProgressAllowed);
        if (isShowProgressAllowed) {
            if (mActiveApiMethods.isEmpty()) {
                //showProgressDialog();
                System.out.println("Başarısız");
            }
            mActiveApiMethods.add(request.getApiMethod());
        }
        mApiManager.send(request);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public final void cancelRequest(ApiMethod method) {
        mActiveApiMethods.remove(method);
        if (!isDestroyed() && mActiveApiMethods.isEmpty()) {
            hideProgressDialog();
            System.out.println("Başarısız");
        }
        mApiManager.cancelRequest(method);
    }
    //endregion

    //region inner classes & implementations
    private ApiListener mApiListener = new ApiListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onApiResponseReceive(ApiMethod method, BaseResponse baseResponse, boolean isSuccess) {
            if (!isSuccess) {
                L.e("API response received: " + method + " : " + baseResponse);
            }
            if (mActiveApiMethods.contains(method)) {
                mActiveApiMethods.remove(method);
                if (!isDestroyed() && mActiveApiMethods.isEmpty()) {
                   // hideProgressDialog();
                    System.out.println("Başarısız");
                }
            }
            if (BaseApiActivity.this == getActiveActivity()) {
                boolean isError = baseResponse.isError();
                if (isError) {
                   // handleErrorResponse(method, baseResponse);
                } else {
                    //handleSuccessResponse(method, baseResponse);
                }
                getApp().onApiResponseReceive(method, baseResponse, baseResponse.isSuccess());
                BaseRequest request = baseResponse.getRequest();
                Object sender = request.getSender();
                if (sender == null) {
                    BaseApiActivity.this.onApiResponseReceive(method, baseResponse, baseResponse.isSuccess());
                    for (ApiListener l : mApiListeners) {
                        l.onApiResponseReceive(method, baseResponse, baseResponse.isSuccess());
                    }
                } else {
                    if (this == sender) {
                        BaseApiActivity.this.onApiResponseReceive(method, baseResponse, baseResponse.isSuccess());
                    } else {
                        for (ApiListener l : mApiListeners) {
                            if (l == sender) {
                                l.onApiResponseReceive(method, baseResponse, baseResponse.isSuccess());
                            }
                        }
                    }
                }
            }
        }
    };


}
