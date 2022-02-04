package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.MenuTab;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.controller.base.BaseFragment;
import com.example.myapplication.controller.fragment.HomeFragment;
import com.example.myapplication.controller.fragment.MarketsFragment;
import com.example.myapplication.controller.fragment.MenuFragment;
import com.example.myapplication.controller.fragment.NewsFragment;
import com.example.myapplication.controller.fragment.SortingFragment;
import com.example.myapplication.controller.fragment.WalletFragment;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.view.layout.BottomTabView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String EXTRA_IS_COMING_FROM_NEWS_NOTIFICATION = getNewExtraId();
    private static final String EXTRA_IS_COMING_FOR_CONTACT_ACTIVITY = getNewExtraId();

    @BindView(R.id.bottomTabLayout)
    BottomTabView bottomTabLayout;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.ibDrawer)
    ImageView ibDrawer;
    @BindView(R.id.tvTitle)
    TextView tvTitle;


    private NewsFragment fragmentNews;
    private WalletFragment fragmentWallet;
    private HomeFragment fragmentHome;
    private SortingFragment fragmentSorting;
    private MarketsFragment fragmentMarkets;

    private MenuTab mSelectedMenuTab;

    private boolean mIsFromNewsNotification;
    private boolean mIsComeForContactActivity;


    public static void startWithNewTask(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void assignObjects() {
        super.assignObjects();
        mIsFromNewsNotification = getIntent().getBooleanExtra(EXTRA_IS_COMING_FROM_NEWS_NOTIFICATION, false);
        mIsComeForContactActivity = getIntent().getBooleanExtra(EXTRA_IS_COMING_FOR_CONTACT_ACTIVITY, false);

        fragmentNews=new NewsFragment();
        fragmentHome=new HomeFragment();
        fragmentMarkets=new MarketsFragment();
        fragmentSorting=new SortingFragment();
        fragmentWallet=new WalletFragment();

    }

    @Override
    public void onLayoutReady() {
        super.onLayoutReady();
            showFragmentBySelectedMenuTab(MenuTab.HOME);
            mSelectedMenuTab = MenuTab.HOME;

    }


    @Override
    public void setListeners() {
        super.setListeners();
        ibDrawer.setOnClickListener(this);
        drawerLayout.addDrawerListener(mDrawerListener);

    }

    @Override
    public void prepareUI() {
        super.prepareUI();
        setWindowBackgroundImage(R.color.base_window_background);
    }

    @Override
    public void onEventReceive(int event, Object... datas) {
        if (event == MenuFragment.EVENT_MAIN_ITEM_CLICK) {
            MenuTab clickedTab = (MenuTab) datas[0];
            showFragmentBySelectedMenuTab(clickedTab);
        } else if (event == MenuFragment.EVENT_MENU_ITEM_CLICK) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else if (event == HomeFragment.EVENT_TRANSACTION_BUTTON_CLICKED) {
            showFragmentBySelectedMenuTab(MenuTab.WALLET);
        } else if (event == BottomTabView.EVENT_TAB_SELECTED) {
            MenuTab clickedTab = (MenuTab) datas[0];
            showFragmentBySelectedMenuTab(clickedTab);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ibDrawer) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void onBackPressed() {
        if(mSelectedMenuTab!=MenuTab.HOME){
            showFragmentBySelectedMenuTab(MenuTab.HOME);
        }else{
            super.onBackPressed();
        }
    }

    private void showFragmentBySelectedMenuTab(MenuTab clickedTab){
        if(clickedTab !=mSelectedMenuTab){
            if(clickedTab==MenuTab.HOME){
                tvTitle.setText("Ana Sayfa");
                    replaceFragment(fragmentHome);
            }else if(clickedTab==MenuTab.NEWS){
                tvTitle.setText("Haberler");
                replaceFragment(fragmentNews);
            }else if(clickedTab==MenuTab.MARKETS){
                tvTitle.setText("Piyasalar");
                replaceFragment(fragmentMarkets);
            }else if(clickedTab==MenuTab.WALLET){
                tvTitle.setText("Cüzdan");
                replaceFragment(fragmentWallet);
            }else if(clickedTab==MenuTab.SORTING){
                tvTitle.setText("Sıralama");
                replaceFragment(fragmentSorting);
            }
            mSelectedMenuTab = clickedTab;
            bottomTabLayout.refreshSelectedTab(clickedTab);

        }
    }



    private void replaceFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();
    }



    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
           //KeyboardUtil.hide(context);
            float slideX = drawerView.getWidth() * slideOffset;
            llContent.setTranslationX(slideX);
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) { }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) { }

        @Override
        public void onDrawerStateChanged(int newState) { }
    };

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
