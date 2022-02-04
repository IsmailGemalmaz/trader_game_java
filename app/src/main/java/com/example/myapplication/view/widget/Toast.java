package com.example.myapplication.view.widget;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import es.dmoral.toasty.Toasty;

public abstract class Toast {
    private static final int DEFAULT_DURATION = android.widget.Toast.LENGTH_LONG;

    public static void info(Context context, int messageResId) {
        info(context, context.getString(messageResId));
    }

    public static void info(Context context, String message) {
        Toasty.custom(context, message, null, ContextCompat.getColor(context, R.color.green), DEFAULT_DURATION, false, true).show();
    }

    public static void error(Context context, String message) {
        Toasty.custom(context, message, null, ContextCompat.getColor(context, R.color.red), DEFAULT_DURATION, false, true).show();
    }
}
