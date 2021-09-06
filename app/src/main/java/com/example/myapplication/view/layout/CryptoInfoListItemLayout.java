package com.example.myapplication.view.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.controller.base.BaseApiActivity;
import com.example.myapplication.controller.dialog.Dialog;
import com.example.myapplication.manager.api.ApiListener;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.view.widget.Toast;

import javax.security.auth.callback.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CryptoInfoListItemLayout extends FrameLayout implements View.OnClickListener, ApiListener {

    public static final int EVENT_STOCK_DROPPED = BaseActivity.getNewEventId();

    private static final int RESOURCE = R.layout.layout_crypto_info_list_item;

    @BindView(R.id.tvCurrency)
    TextView tvCurrency;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMarketCap)
    TextView tvMArketCap;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.llCryotItem)
    LinearLayout llCryptoItem;
    @BindView(R.id.ivFavorite)
    ImageView ivFavorite;

    private BaseApiActivity mActivity;
    private Context mContext;
    private CryptoStock mStock;
    private Callback mCallback;

    public CryptoInfoListItemLayout(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(RESOURCE, this,true);
        ButterKnife.bind(this);
        mActivity = (BaseApiActivity) getContext();
        mContext = context;
        llCryptoItem.setOnClickListener(this);
        ivFavorite.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v==llCryptoItem){
            Dialog.showDialog(mActivity,mStock,-1,false);
        }else if(v==ivFavorite){
            Toast.info(mContext,"Favorilere Eklendi");

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActivity.addApiListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActivity.removeApiListener(this);
    }


    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }


    public void fillContent(CryptoStock stock,int position){

        for( int i=1;stock.getCurrency().length()<i;i++){
            tvNumber.setText(i);
        }
        mStock=stock;
        tvCurrency.setText(stock.getCurrency());
        tvPrice.setText(String.valueOf("$"+stock.getPrice()));
        tvName.setText(String.valueOf(stock.getName()));
        tvMArketCap.setText(String.valueOf( "P.değ $"+stock.getMarket_cap()));

    }


//    <ImageView
//    android:layout_width="65dp"
//    android:layout_height="35dp"
//    android:layout_gravity="center"
//    android:layout_marginLeft="5dp"
//    android:background="@drawable/shape_crypto_changed_green"
//    android:gravity="end"
//    android:layout_marginRight="10dp"
//
//            />
}
