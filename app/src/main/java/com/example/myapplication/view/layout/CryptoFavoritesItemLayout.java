package com.example.myapplication.view.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.controller.base.BaseApiActivity;
import com.example.myapplication.model.entity.CryptoFavoritesStcok;
import com.example.myapplication.model.entity.CryptoStock;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CryptoFavoritesItemLayout extends FrameLayout {

    private static final int RESOURCE = R.layout.layout_crypto_favorites_item;

    @BindView(R.id.tvFvCurrency)
    TextView tvFvCurrency;
    @BindView(R.id.tvfvName)
    TextView tvFvName;
    @BindView(R.id.tvFvPrice)
    TextView tvFvPrice;
    @BindView(R.id.tvFvMarketCap)
    TextView tvFvMarketCap;

    private BaseApiActivity mActivity;
    private Context mContext;


    public CryptoFavoritesItemLayout(@NonNull Context context) {
        super(context);

        LayoutInflater.from(context).inflate(RESOURCE, this,true);
        ButterKnife.bind(this);
        mActivity = (BaseApiActivity) getContext();
        mContext = context;
    }


    public void fillContent(CryptoFavoritesStcok stock, int position){

        tvFvCurrency.setText(stock.getCurrency());
        tvFvName.setText(stock.getName());
        tvFvMarketCap.setText(stock.getMarket_cap());
        tvFvPrice.setText(stock.getPrice());

    }
}
