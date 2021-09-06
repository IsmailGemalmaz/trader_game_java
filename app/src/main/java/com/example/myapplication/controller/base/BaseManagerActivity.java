package com.example.myapplication.controller.base;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.myapplication.manager.BaseManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseManagerActivity extends BaseApiActivity {
    private List<BaseManager> mManagers=new ArrayList<>();

    public void registerManager(BaseManager baseManager){
        mManagers.add(baseManager);
    }

    public void unregisterManager(BaseManager baseManager){
        mManagers.remove(baseManager);
    }

    @Override
    public void onCreated() {
        super.onCreated();
        for (BaseManager manager : mManagers) {
            manager.onCreated();
        }
    }

    @Override
    public void onStarted() {
        super.onStarted();
        for (BaseManager manager : mManagers) {
            manager.onStarted();
        }
    }

    @Override
    public void onResumed() {
        super.onResumed();
        for (BaseManager manager : mManagers) {
            manager.onResumed();
        }
    }

    @Override
    public void onLayoutReady() {
        super.onLayoutReady();
        for (BaseManager manager : mManagers) {
            manager.onLayoutReady();
        }
    }

    @Override
    public void onPaused() {
        super.onPaused();
        for (BaseManager manager : mManagers) {
            manager.onPaused();
        }
    }

    @Override
    public void onStopped() {
        super.onStopped();
        for (BaseManager manager : mManagers) {
            manager.onStopped();
        }
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        Iterator<BaseManager> iterator = mManagers.iterator();
        while (iterator.hasNext()) {  //hasnext Hala dizide eleman varsa true döner değilse false döner
            BaseManager manager = iterator.next();
            manager.onDestroyed();
            manager.destroy();
            iterator.remove();
           // removeApiListener(manager);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (BaseManager manager : mManagers) {
            manager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (BaseManager manager : mManagers) {
            manager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
