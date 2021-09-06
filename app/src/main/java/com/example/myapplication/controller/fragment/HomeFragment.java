package com.example.myapplication.controller.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.controller.activity.FavoriteActivity;
import com.example.myapplication.controller.base.BaseFragment;
import com.example.myapplication.controller.dialog.GetInformatiomDialog;

import butterknife.BindView;

public class HomeFragment  extends BaseFragment implements View.OnClickListener {

    public static final int EVENT_TRANSACTION_BUTTON_CLICKED = getNewEventId();


    @BindView(R.id.llGetInformation)
    LinearLayout llGetInformation;
    @BindView(R.id.llLearnVideo)
    LinearLayout llLearnVideo;
    @BindView(R.id.llComments)
    LinearLayout llComments;
    @BindView(R.id.llOparationPast)
    LinearLayout llOparationPast;
    @BindView(R.id.llFavorite)
    LinearLayout llFavorite;
    @BindView(R.id.llNatification)
    LinearLayout llNatification;
    @BindView(R.id.llDepositMany)
    LinearLayout llDepositMany;
    @BindView(R.id.llBaseClick)
    LinearLayout llBaseClick;

    @Override
    public void setListeners() {
        llGetInformation.setOnClickListener(this);
        llLearnVideo.setOnClickListener(this);
        llComments.setOnClickListener(this);
        llOparationPast.setOnClickListener(this);
        llFavorite.setOnClickListener(this);
        llNatification.setOnClickListener(this);
        llDepositMany.setOnClickListener(this);
        llBaseClick.setOnClickListener(this);
        super.setListeners();
    }

    @Override
    public void onClick(View v) {
        if(v==llGetInformation){
            GetInformatiomDialog.showDialog(activity);
        }else if(v==llFavorite){
            FavoriteActivity.start(activity);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
}
