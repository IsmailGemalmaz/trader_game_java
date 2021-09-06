package com.example.myapplication.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.constant.MenuTab;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.controller.base.BaseApiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomTabView extends FrameLayout implements View.OnClickListener {

    public static final int EVENT_TAB_SELECTED= BaseActivity.getNewEventId();
    private final int RESOURCE= R.layout.widget_bottom_tab_view;

    @BindView(R.id.llHome)
    LinearLayout llHome;
    @BindView(R.id.llNews)
    LinearLayout llNews;
    @BindView(R.id.llMarkets)
    LinearLayout llMarkets;
    @BindView(R.id.llWallet)
    LinearLayout llWallet;
    @BindView(R.id.llSorting)
    LinearLayout llSorting; 
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.ivNews)
    ImageView ivNews;
    @BindView(R.id.ivMarkets)
    ImageView ivMarkets;
    @BindView(R.id.ivWallet)
    ImageView ivWallet;
    @BindView(R.id.ivSorting)
    ImageView ivSorting;

    private BaseApiActivity mActivity;
    private int mSelectedTabBgColor;
    private int mUnselectedTabBgColor;

    private MenuTab mSelectedTab;

    public BottomTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(RESOURCE,this,true);
        ButterKnife.bind(this);
        mActivity=(BaseApiActivity) context;
        mSelectedTab=MenuTab.HOME;
        mSelectedTabBgColor = ContextCompat.getColor(context, R.color.bottom_tab_view_selected);
        mUnselectedTabBgColor = ContextCompat.getColor(context, R.color.bottom_tab_view_unselected);
        llHome.setOnClickListener(this);
        llMarkets.setOnClickListener(this);
        llNews.setOnClickListener(this);
        llSorting.setOnClickListener(this);
        llWallet.setOnClickListener(this);
        refreshTabStates();
    }

    @Override
    public void onClick(View v) {
        if(v==llHome){
            if(mSelectedTab!=MenuTab.HOME){
                mSelectedTab=MenuTab.HOME;
                mActivity.sendEvent(EVENT_TAB_SELECTED,mSelectedTab);
            }
        }else if(v==llNews){
            if(mSelectedTab!=MenuTab.NEWS){
                mSelectedTab=MenuTab.NEWS;
                mActivity.sendEvent(EVENT_TAB_SELECTED,mSelectedTab);
            }
        }else if(v==llMarkets){
            if(mSelectedTab!=MenuTab.MARKETS){
                mSelectedTab=MenuTab.MARKETS;
                mActivity.sendEvent(EVENT_TAB_SELECTED,mSelectedTab);
            }
        }else if(v==llWallet){
            if(mSelectedTab!=MenuTab.WALLET){
                mSelectedTab=MenuTab.WALLET;
                mActivity.sendEvent(EVENT_TAB_SELECTED,mSelectedTab);
            }
        }else if(v==llSorting){
            if(mSelectedTab!=MenuTab.SORTING){
                mSelectedTab=MenuTab.SORTING;
                mActivity.sendEvent(EVENT_TAB_SELECTED,mSelectedTab);
            }
        }
    }

    public void refreshSelectedTab(MenuTab tab){
        mSelectedTab=tab;
        refreshTabStates();
    }

    private void refreshTabStates(){
        if(mSelectedTab==MenuTab.HOME){
            ivHome.setImageResource(R.drawable.bv_dark_home);
            ivNews.setImageResource(R.drawable.bv_news);
            ivMarkets.setImageResource(R.drawable.bv_logo);
            ivWallet.setImageResource(R.drawable.bv_wallet);
            ivSorting.setImageResource(R.drawable.bv_sorting);
            llHome.setBackgroundColor(mSelectedTabBgColor);
            llNews.setBackgroundColor(mUnselectedTabBgColor);
            llMarkets.setBackgroundColor(mUnselectedTabBgColor);
            llWallet.setBackgroundColor(mUnselectedTabBgColor);
            llSorting.setBackgroundColor(mUnselectedTabBgColor);
        }else if(mSelectedTab==MenuTab.NEWS){
            ivHome.setImageResource(R.drawable.bv_home);
            ivNews.setImageResource(R.drawable.bv_dark_news);
            ivMarkets.setImageResource(R.drawable.bv_logo);
            ivWallet.setImageResource(R.drawable.bv_wallet);
            ivSorting.setImageResource(R.drawable.bv_sorting);
            llHome.setBackgroundColor(mUnselectedTabBgColor);
            llNews.setBackgroundColor(mSelectedTabBgColor);
            llMarkets.setBackgroundColor(mUnselectedTabBgColor);
            llWallet.setBackgroundColor(mUnselectedTabBgColor);
            llSorting.setBackgroundColor(mUnselectedTabBgColor);
        }else if(mSelectedTab==MenuTab.MARKETS){
            ivHome.setImageResource(R.drawable.bv_home);
            ivNews.setImageResource(R.drawable.bv_news);
            ivMarkets.setImageResource(R.drawable.bv_dark_logo);
            ivWallet.setImageResource(R.drawable.bv_wallet);
            ivSorting.setImageResource(R.drawable.bv_sorting);
            llHome.setBackgroundColor(mUnselectedTabBgColor);
            llNews.setBackgroundColor(mUnselectedTabBgColor);
            llMarkets.setBackgroundColor(mSelectedTabBgColor);
            llWallet.setBackgroundColor(mUnselectedTabBgColor);
            llSorting.setBackgroundColor(mUnselectedTabBgColor);
        }else if(mSelectedTab==MenuTab.WALLET){
            ivHome.setImageResource(R.drawable.bv_home);
            ivNews.setImageResource(R.drawable.bv_news);
            ivMarkets.setImageResource(R.drawable.bv_logo);
            ivWallet.setImageResource(R.drawable.bv_dark_wallet);
            ivSorting.setImageResource(R.drawable.bv_sorting);
            llHome.setBackgroundColor(mUnselectedTabBgColor);
            llNews.setBackgroundColor(mUnselectedTabBgColor);
            llMarkets.setBackgroundColor(mUnselectedTabBgColor);
            llWallet.setBackgroundColor(mSelectedTabBgColor);
            llSorting.setBackgroundColor(mUnselectedTabBgColor);
        }else if(mSelectedTab==MenuTab.SORTING){
            ivHome.setImageResource(R.drawable.bv_home);
            ivNews.setImageResource(R.drawable.bv_news);
            ivMarkets.setImageResource(R.drawable.bv_logo);
            ivWallet.setImageResource(R.drawable.bv_wallet);
            ivSorting.setImageResource(R.drawable.bv_dark_sorting);
            llHome.setBackgroundColor(mUnselectedTabBgColor);
            llNews.setBackgroundColor(mUnselectedTabBgColor);
            llMarkets.setBackgroundColor(mUnselectedTabBgColor);
            llWallet.setBackgroundColor(mUnselectedTabBgColor);
            llSorting.setBackgroundColor(mSelectedTabBgColor);
        }
    }



}
