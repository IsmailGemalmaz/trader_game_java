package com.example.myapplication.controller.fragment;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.MenuTab;
import com.example.myapplication.controller.base.BaseFragment;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.view.layout.BottomTabView;

import butterknife.BindView;

public class MenuFragment extends BaseFragment implements View.OnClickListener {

    public static final int EVENT_MENU_ITEM_CLICK = getNewEventId();
    public static final int EVENT_MAIN_ITEM_CLICK = getNewEventId();

    @BindView(R.id.llProfile)
    LinearLayout llProfile;
    @BindView(R.id.flHome)
    FrameLayout flHome;
    @BindView(R.id.flNews)
    FrameLayout flNews;
    @BindView(R.id.flMarkets)
    FrameLayout flMarkets;
    @BindView(R.id.flWallet)
    FrameLayout flWallet;
    @BindView(R.id.flSorting)
    FrameLayout flSorting;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserMany)
    TextView tvUserMany;



    private MenuTab mCurrentlySelectedMenuTab = MenuTab.HOME;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public void setListeners() {
        super.setListeners();
        llProfile.setOnClickListener(this);
        flHome.setOnClickListener(this);
        flNews.setOnClickListener(this);
        flMarkets.setOnClickListener(this);
        flWallet.setOnClickListener(this);
        flSorting.setOnClickListener(this);

    }

    @Override
    public void prepareUI() {
        super.prepareUI();
        updateSelectedMenuBackground(mCurrentlySelectedMenuTab);
    }

    @Override
    public void onClick(View v) {

        sendEvent(EVENT_MENU_ITEM_CLICK);
        if(v==flHome){
            sendEvent(EVENT_MAIN_ITEM_CLICK,MenuTab.HOME);
        }else if(v==flNews){
            sendEvent(EVENT_MAIN_ITEM_CLICK,MenuTab.NEWS);
        }else if(v==flMarkets){
            sendEvent(EVENT_MAIN_ITEM_CLICK,MenuTab.MARKETS);
        }else if(v==flWallet||v==llProfile){
            sendEvent(EVENT_MAIN_ITEM_CLICK,MenuTab.WALLET);
        }
    }

    @Override
    public void onEventReceive(int event, Object... data) {
        if (event == EVENT_MAIN_ITEM_CLICK || event == BottomTabView.EVENT_TAB_SELECTED) {
            MenuTab selectedMenuTab = (MenuTab) data[0];
            updateSelectedMenuBackground(selectedMenuTab);
        }else if (event == HomeFragment.EVENT_TRANSACTION_BUTTON_CLICKED) {
            updateSelectedMenuBackground(MenuTab.HOME);
        }
    }

    private void updateSelectedMenuBackground(MenuTab selectedMenuTab) {
        flHome.setBackgroundColor(0);
        flNews.setBackgroundColor(0);
        flMarkets.setBackgroundColor(0);
        flWallet.setBackgroundColor(0);
        flSorting.setBackgroundColor(0);
        if (selectedMenuTab == MenuTab.HOME) {
            flHome.setBackgroundColor(ContextCompat.getColor(context, R.color.bottom_tab_view_selected));
        } else if (selectedMenuTab == MenuTab.MARKETS) {
            flMarkets.setBackgroundColor(ContextCompat.getColor(context, R.color.bottom_tab_view_selected));
        } else if (selectedMenuTab == MenuTab.WALLET) {
            flWallet.setBackgroundColor(ContextCompat.getColor(context, R.color.bottom_tab_view_selected));
        } else if (selectedMenuTab == MenuTab.NEWS) {
            flNews.setBackgroundColor(ContextCompat.getColor(context, R.color.bottom_tab_view_selected));
        }else if (selectedMenuTab == MenuTab.SORTING) {
            flSorting.setBackgroundColor(ContextCompat.getColor(context, R.color.bottom_tab_view_selected));
        }
        mCurrentlySelectedMenuTab = selectedMenuTab;
    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
