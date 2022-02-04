package com.example.myapplication.controller.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import butterknife.ButterKnife;

public abstract class BaseTamplateDialog extends DialogFragment {
    public Context context;
    public BaseTemplateActivity activity;

    private ViewGroup vgContent;
    private boolean mIsActivityRecreated;

    public abstract int getLayoutId();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (BaseTemplateActivity) getActivity();
        context = activity;
        int layoutId = getLayoutId();
        vgContent = (ViewGroup) inflater.inflate(layoutId, null);
        ButterKnife.bind(this, vgContent);
        mIsActivityRecreated = activity.isRecreated();
        if (!mIsActivityRecreated) {
            initialize();
            onCreated();
        }
        return vgContent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public final void onStart() {
        super.onStart();
        if (!mIsActivityRecreated) {
            onStarted();
        }
    }

    @Override
    public final void onResume() {
        super.onResume();
        if (!mIsActivityRecreated) {
            onResumed();
        }
    }

    @Override
    public final void onPause() {
        super.onPause();
        if (!mIsActivityRecreated) {
            onPaused();
        }
    }

    @Override
    public final void onStop() {
        super.onStop();
        if (!mIsActivityRecreated) {
            onStopped();
        }
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        if (!mIsActivityRecreated) {
            onDestroyed();
        }
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
    }

    private void initialize() {
        createViews();
        assignObjects();
        setListeners();
        prepareUI();
        setOnGlobalLayoutListenerToContentView();
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
