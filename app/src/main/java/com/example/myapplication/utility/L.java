package com.example.myapplication.utility;

import android.util.Log;

import com.example.myapplication.constant.ProjectSettings;

public class L {

    private static final String TAG = ProjectSettings.LOG_TAG;
    private static final int MAX_LOG_SIZE = 1000;

    public static void e(Object msg) {
        e(TAG, msg);
    }

    public static void w(Object msg) {
        w(TAG, msg);
    }

    public static void d(Object msg) {
        d(TAG, msg);
    }

    public static void i(Object msg) {
        i(TAG, msg);
    }

    public static void v(Object msg) {
        v(TAG, msg);
    }

    public static void wtf(Object msg) {
        wtf(TAG, msg);
    }

    public static void e(Object tag, Object msg) {

//        if (ProjectSettings.IS_DEBUG) {
        for (int i = 0; i <= msg.toString().length() / MAX_LOG_SIZE; i++) {
            int start = i * MAX_LOG_SIZE;
            int end = (i + 1) * MAX_LOG_SIZE;
            end = end > msg.toString().length() ? msg.toString().length() : end;
            Log.e(tag.toString(), msg.toString().substring(start, end));
        }
//        }
    }

    public static void w(Object tag, Object msg) {
        if (ProjectSettings.IS_DEBUG) {
            Log.w(tag.toString(), msg.toString());
        }
    }

    public static void d(Object tag, Object msg) {
        if (ProjectSettings.IS_DEBUG) {
            Log.d(tag.toString(), msg.toString());
        }
    }

    public static void i(Object tag, Object msg) {
        if (ProjectSettings.IS_DEBUG) {
            Log.i(tag.toString(), msg.toString());
        }
    }

    public static void v(Object tag, Object msg) {
        if (ProjectSettings.IS_DEBUG) {
            Log.v(tag.toString(), msg.toString());
        }
    }

    public static void wtf(Object tag, Object msg) {
        if (ProjectSettings.IS_DEBUG) {
            Log.wtf(tag.toString(), msg.toString());
        }
    }
}
