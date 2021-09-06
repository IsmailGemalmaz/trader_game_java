package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.controller.base.BaseActivity;

import butterknife.BindView;

public class FavoriteActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ibFavoriteBack)
    ImageButton ibFavoriteBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_favorite;

    }

    @Override
    public void setListeners() {
        ibFavoriteBack.setOnClickListener(this);
    }

    public static void start(Activity activity){
        Intent intent=new Intent(activity,FavoriteActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v==ibFavoriteBack) {
            onBackPressed();
        }
    }


}
