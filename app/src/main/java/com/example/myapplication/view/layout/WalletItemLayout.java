package com.example.myapplication.view.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.base.BaseApiActivity;
import com.example.myapplication.manager.api.ApiListener;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.response.BaseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class    WalletItemLayout extends FrameLayout implements ApiListener {


    @BindView(R.id.tvWalletCurrency)
    TextView tvCurrency;
    @BindView(R.id.tvWalletName)
    TextView tvName;
    @BindView(R.id.tvWalletPrice)
    TextView tvPrice;

    Context mContext;
    private BaseApiActivity mActivity;

    private static final int RESOURCE = R.layout.layout_item_wallet;

    public WalletItemLayout(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(RESOURCE, this,true);
        ButterKnife.bind(this);
        mActivity = (BaseApiActivity) getContext();
        mContext = context;
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


    public void fillContent(CryptoStock stock, int position) {

        tvCurrency.setText(stock.getCurrency());
        tvName.setText(stock.getName());
        tvPrice.setText(""+stock.getPrice());
    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {

    }
}
