package com.example.myapplication.controller.dialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;
import com.example.myapplication.constant.ProjectSettings;
import com.example.myapplication.controller.base.BaseDialog;

import butterknife.BindView;

public class ProgresDialog extends BaseDialog {

    private static final String EXTRA_TEXT = "extra.text";

    public static ProgressDialog show(FragmentActivity activity, String text) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        ProgressDialog dialog = new ProgressDialog(activity);
       // dialog.setArguments(args);
       // dialog.show(activity);
        return dialog;
    }

    @BindView(R.id.textView)
    TextView textView;

    private String mText;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_progres;
    }

    @Override
    public void assignObjects() {
        mText = getArguments().getString(EXTRA_TEXT);
    }

    @Override
    public void prepareUI() {
        setCancelable(ProjectSettings.PROGRESS_DIALOG_CANCELABLE_ENABLED);
        if (TextUtils.isEmpty(mText)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(mText);
        }
    }

}
