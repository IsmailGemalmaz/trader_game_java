package com.example.myapplication.controller.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.myapplication.App;
import com.example.myapplication.controller.EventListener;

public abstract class BaseUtilityFragment extends BaseTamplateFragment implements EventListener {

    public static int getNewEventId() {
        return BaseActivity.getNewEventId();
    }

    public static int getNewRequestCode() {
        return BaseActivity.getNewRequestCode();
    }

    public void onHideKeyboard() {}

    public void onShowKeyboard(int keyboardHeight) {}

    @Override
    public void onEventReceive(int event, Object... data) {}

    @Override
    public void onCreated() {
        super.onCreated();
        ((BaseUtilityActivity) activity).addEventListener(this);
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        ((BaseApiActivity) activity).removeEventListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((BaseUtilityActivity) getActivity()).setRunning(true);
    }

    @Override
    public void startActivity(Intent intent) {
        ((BaseUtilityActivity) getActivity()).setStartingAnotherActivity(true);
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        ((BaseUtilityActivity) getActivity()).setStartingAnotherActivity(true);
        super.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        ((BaseUtilityActivity) getActivity()).setStartingAnotherActivity(true);
        activity.startActivityForResult(intent, requestCode);
    }

    public void sendEvent(int event, Object... datas) {
        ((BaseUtilityActivity) getActivity()).sendEvent(event, datas);
    }

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void showProgressDialog(int textResId) {
        showProgressDialog(getString(textResId));
    }


    public void showProgressDialog(final String text) {
        ((BaseUtilityActivity) getActivity()).showProgressDialog(text);
    }

    public void hideProgressDialog() {
        ((BaseUtilityActivity) getActivity()).hideProgressDialog();
    }

    /*public PreferenceHelper getPreference() {
        return ((BaUtilityActivity) activity).getPreference();
    }*/

    public App getApp() {
        return ((BaseUtilityActivity) activity).getApp();
    }

}
