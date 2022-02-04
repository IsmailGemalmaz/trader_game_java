package com.example.myapplication.controller.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;
import com.example.myapplication.controller.base.BaseDialog;

import butterknife.BindView;

public class GetInformatiomDialog extends BaseDialog implements View.OnClickListener {

    public static final int EVENT_STOCK_TRANSACTION_IS_DONE = getNewEventId();


    @BindView(R.id.rlLink)
    RelativeLayout rlLınk;
    @BindView(R.id.ibClose)
    ImageButton ibClose;

    public static void showDialog(FragmentActivity activity) {
        GetInformatiomDialog dialog = new GetInformatiomDialog();
        dialog.show(activity);
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme_NoActionBar_Translucent);
    }

    @Override
    public void setListeners() {
        super.setListeners();
        ibClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==ibClose){
            dismiss();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_get_information;
    }




}
