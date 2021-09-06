package com.example.myapplication.controller.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.App;
import com.example.myapplication.constant.ProjectSettings;
import com.example.myapplication.controller.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseUtilityActivity extends BaseTemplateActivity implements EventListener {

    //region statics
    private static AtomicInteger sEventIdGeneratorIndex = new AtomicInteger(1);
    private static AtomicInteger sRequestCodeGeneratorIndex = new AtomicInteger(1);
    private static AtomicInteger sExtraIdGeneratorIndex = new AtomicInteger(1);
    private static BaseTemplateActivity sActiveActivity;
    private static List<EventListener> sEventListeners;
    private static boolean sIsInForeground;

    public static boolean isInForeground() {
        return sIsInForeground;
    }

    public static BaseTemplateActivity getActiveActivity() {
        return sActiveActivity;
    }

    public static int getNewEventId() {
        return sEventIdGeneratorIndex.getAndIncrement();
    }

    public static int getNewRequestCode() {
        return sRequestCodeGeneratorIndex.getAndIncrement();
    }

    public static String getNewExtraId() {
        return "extra_" + sExtraIdGeneratorIndex.getAndIncrement();
    }
    //endregion

    //region class variables
    private List<BaseUtilityFragment> mActiveFragments;
    private ProgressDialog mProgressDialog;
    private boolean mIsRunning;
    private boolean mIsStartingAnotherActivity;
    private boolean mIsKeyboardVisible;
    //endregion

    //region callback & abstract methods
    public void onHideKeyboard() {}

    public void onShowKeyboard(int keyboardHeight) {}

    @Override
    public void onEventReceive(int event, Object... datas) {}
    //endregion

    //region lifecyclemethods
    @Override
    public void onCreated() {
        super.onCreated();
        if (sEventListeners == null) {
            sEventListeners = new ArrayList<>();
        }
        mActiveFragments = new ArrayList<>();
        sActiveActivity = this;
        mIsRunning = true;
        if (ProjectSettings.ACTIVITY_KEYBOARD_LISTENER_ENABLED) {
            setKeyboardListener();
        }


    }

    @Override
    public void onStarted() {
        super.onStarted();
        mIsRunning = true;
    }

    @Override
    public void onResumed() {
        super.onResumed();
        sActiveActivity = this;
        sIsInForeground = true;
        mIsStartingAnotherActivity = false;
    }

    @Override
    public void onPaused() {
        super.onPaused();
        sIsInForeground = false;
    }

    @Override
    public void onStopped() {
        super.onStopped();
        mIsRunning = false;
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        sEventListeners.remove(this);
//        if (ProjectSettings.ACTIVITY_KEYBOARD_LISTENER_ENABLED) {
//            removeKeyboardListener();
//        }
    }

    @CallSuper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mIsRunning = true;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        mIsStartingAnotherActivity = true;
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        mIsStartingAnotherActivity = true;
        super.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        mIsStartingAnotherActivity = true;
        super.startActivityForResult(intent, requestCode);
    }
    //endregion

    //region public methods
    public App getApp() {
        return (App) getApplication();
    }

    public Fragment findFragment(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }

    public void setRunning(boolean val) {
        mIsRunning = val;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void setStartingAnotherActivity(boolean startingAnotherActivity) {
        mIsStartingAnotherActivity = startingAnotherActivity;
    }

    public boolean isStartingAnotherActivity() {
        return mIsStartingAnotherActivity;
    }

    /*
    public PreferenceHelper getPreference() {
        return mPreferenceHelper;
    }
*/
    public void addEventListener(EventListener listener) {
        sEventListeners.add(listener);
    }

    public void removeEventListener(EventListener listener) {
        sEventListeners.remove(listener);
    }

    public void sendEvent(int event, Object... datas) {
        onEventReceive(event, datas);
        for (EventListener l : sEventListeners) {
            l.onEventReceive(event, datas);
        }
    }

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void showProgressDialog(int textResId) {
        showProgressDialog(getString(textResId));
    }

    public void showProgressDialog(final String text) {
        getContentView().post(new Runnable() {
            @Override
            public void run() {
                if (isRunning()) {
                   // mProgressDialog = ProgressDialog.show(activity, text);
                }
            }
        });
    }

    public void hideProgressDialog() {
        getContentView().post(new Runnable() {
            @Override
            public void run() {
                if (isRunning() && mProgressDialog != null) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        });
    }

    public void setWindowBackgroundImage(int imgResId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imgResId);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((ViewGroup) getWindow().getDecorView()).addView(imageView, 0);
    }
    // endregion

    //region private methods
    private void setKeyboardListener() {
        vgContent.getViewTreeObserver().addOnGlobalLayoutListener(mKeyboardLayoutListener);
    }

    private void removeKeyboardListener() {
        vgContent.getViewTreeObserver().removeOnGlobalLayoutListener(mKeyboardLayoutListener);
    }
    //endregion

    //region inner classes & implementations
    private ViewTreeObserver.OnGlobalLayoutListener mKeyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int navigationBarHeight = 0;
            int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
            int statusBarHeight = 0;
            resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
            Rect rect = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int keyboardHeight = vgContent.getRootView().getHeight() - (statusBarHeight + navigationBarHeight + rect.height());
            if (keyboardHeight <= 0 && mIsKeyboardVisible) {
                mIsKeyboardVisible = false;
                onHideKeyboard();
                for (BaseUtilityFragment fragment : mActiveFragments) {
                    fragment.onHideKeyboard();
                }
            } else if (keyboardHeight > 0 && !mIsKeyboardVisible) {
                mIsKeyboardVisible = true;
                onShowKeyboard(keyboardHeight);
                for (BaseUtilityFragment fragment : mActiveFragments) {
                    fragment.onShowKeyboard(keyboardHeight);
                }
            }
        }
    };




    /*
    public void addEventListener(EventListener listener) {
        sEventListeners.add(listener);
    }

    public void removeEventListener(EventListener listener) {
        sEventListeners.remove(listener);
    }

    public void sendEvent(int event, Object... datas) {
        onEventReceive(event, datas);
        for (EventListener l : sEventListeners) {
            l.onEventReceive(event, datas);
        }
    }


    public void setWindowBackgroundImage(int imgResId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imgResId);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((ViewGroup) getWindow().getDecorView()).addView(imageView, 0);
    }*/
}
