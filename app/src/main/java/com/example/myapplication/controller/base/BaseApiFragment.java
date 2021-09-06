package com.example.myapplication.controller.base;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.manager.api.ApiListener;
import com.example.myapplication.manager.api.ApiManager;
import com.example.myapplication.model.request.BaseRequest;
import com.example.myapplication.model.response.BaseResponse;

public abstract class BaseApiFragment extends BaseUtilityFragment implements ApiListener {

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {}

    public void sendRequest(BaseRequest request) {
        ((BaseApiActivity) activity).sendRequest(request);
    }

    public void sendRequest(BaseRequest request, boolean isShowProgress) {
        ((BaseApiActivity) activity).sendRequest(request, isShowProgress);
    }

    public void sendEvent(int event, Object... datas) {
        ((BaseApiActivity) activity).sendEvent(event, datas);
    }

    public ApiManager getApiManager() {
        return ((BaseApiActivity) activity).getApiManager();
    }

    public void addApiListener(ApiListener listener) {
        ((BaseApiActivity) activity).addApiListener(listener);
    }

    public void removeApiListener(ApiListener listener) {
        ((BaseApiActivity) activity).removeApiListener(listener);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        addApiListener(this);
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        removeApiListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public final void cancelRequest(ApiMethod method) {
        ((BaseApiActivity) activity).cancelRequest(method);
    }


}
