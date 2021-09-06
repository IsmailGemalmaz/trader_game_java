package com.example.myapplication.controller.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public  abstract class BaseTemplateActivity extends AppCompatActivity  {
    public Context context=this;
    public BaseTemplateActivity activity=this;

    @BindView(android.R.id.content)
    ViewGroup vgContent;

    private boolean mIsRecreated;

    public int getLayoutId() {
        return -1;
    }

    public void createViews() {}

    public void assignObjects() {}

    public void setListeners() {}

    public void prepareUI() {}

    @CallSuper
    public void onLayoutReady() {}

    @CallSuper
    public void onCreated() {}

    @CallSuper
    public void onStarted() {}

    @CallSuper
    public void onResumed() {}

    @CallSuper
    public void onPaused() {}

    @CallSuper
    public void onStopped() {}

    @CallSuper
    public void onDestroyed() {}

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsRecreated = savedInstanceState != null;
        if (mIsRecreated) {
            restartApp();
        } else {
            onCreated();
            initialize();


        }
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected final void onStart() {
        super.onStart();
        if (!mIsRecreated) {
            onStarted();
        }
    }

    @Override
    protected final void onResume() {
        super.onResume();
        if (!mIsRecreated) {
            onResumed();
        }
    }

    @Override
    protected final void onPause() {
        super.onPause();
        if (!mIsRecreated) {
            onPaused();
        }
    }

    @Override
    protected final void onStop() {
        super.onStop();
        if (!mIsRecreated) {
            onStopped();
        }
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        if (!mIsRecreated) {
            onDestroyed();
        }
    }

    public boolean isRecreated() {
        return mIsRecreated;
    }

    public ViewGroup getContentView() {
        return vgContent;
    }

    private void initialize() {
        int layoutId = getLayoutId();
        if (layoutId != -1) {
            setContentView(layoutId);
        }
        ButterKnife.bind(this);
        createViews();
        assignObjects();
        setListeners();
        prepareUI();
       setOnGlobalLayoutListenerToContentView();
    }

    private void restartApp() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    private void setOnGlobalLayoutListenerToContentView() {
        vgContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                vgContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                vgContent.post(new Runnable() {
                    @Override
                    public void run() {
                        onLayoutReady();
                    }
                });
            }
        });
    }
}
