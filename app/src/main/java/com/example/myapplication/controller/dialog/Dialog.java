package com.example.myapplication.controller.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.controller.activity.CryptoStockDetailActivity;
import com.example.myapplication.controller.base.BaseDialog;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;

import butterknife.BindView;

public class Dialog  extends BaseDialog implements View.OnClickListener {


    @BindView(R.id.btnDetail)
    Button btnDetail;
    @BindView(R.id.btnBuy) Button btnBuy;
    @BindView(R.id.btnCancel) Button btnCancel;
    @BindView(R.id.llDialog)
    LinearLayout llDialog;

    private CryptoStock mStock;
    private int mTransactionType;
    private boolean mIsDetailButtonHidden;

    public static void showDialog(FragmentActivity activity, CryptoStock stock, int transactionType, boolean isDetailButtonHidden) {
        Dialog alert = new Dialog();
        alert.setStock(stock);
        //alert.setDetailButtonHidden(isDetailButtonHidden);
        //alert.setTransactionType(transactionType);
       alert.show(activity);
    }

    @Override
    public void setListeners() {
        super.setListeners();
        btnBuy.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDetail.setOnClickListener(this);
        llDialog.setOnClickListener(this);
    }

    @Override
    public void prepareUI() {
        super.prepareUI();
        setButtonVisibilty();
    }

    @Override
    public void onClick(View v) {
        if(v==btnCancel){
            dismiss();
        }else if(v==btnDetail){
            CryptoStockDetailActivity.start(activity,mStock.getCurrency(),mStock.getId());
            dismiss();
        }else if(v==btnBuy){

        }else if(v==llDialog){
            dismiss();
        }
    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {
        super.onApiResponseReceive(method, response, isSuccess);

        if(ApiMethod.GET_CRYPTO_STOCK==method && isSuccess){
            if(mStock !=null){
                GetCryptoStockResponse cryptoStockResponse=(GetCryptoStockResponse) response;
            }
        }
    }

    public void setStock(CryptoStock stock) {
        mStock = stock;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog;
    }

    private void setButtonVisibilty(){

    }
}
