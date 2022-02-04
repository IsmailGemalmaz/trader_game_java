package com.example.myapplication.controller.fragment;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.FilterType;
import com.example.myapplication.controller.base.BaseFragment;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.request.GetCryptoWalletRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;
import com.example.myapplication.model.response.GetCryptoWalletResponse;
import com.example.myapplication.view.adapter.CryptoWalletItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WalletFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.rvWallet)
    RecyclerView rvWalet;

    private CryptoWalletItemAdapter mAdapter;
    private ArrayList<CryptoStock> mWalletList;



    @Override
    public void assignObjects() {
        super.assignObjects();
        mWalletList=new ArrayList<>();
        mAdapter=new CryptoWalletItemAdapter(context,mWalletList);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void prepareUI() {
        super.prepareUI();
        rvWalet.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvWalet.setAdapter(mAdapter);
    }

    @Override
    public void onLayoutReady() {
        super.onLayoutReady();
        sendGetWalletRequest();
    }

    private void sendGetWalletRequest() {
        GetCryptoWalletRequest getCryptoWalletRequest=new GetCryptoWalletRequest();
        sendRequest(getCryptoWalletRequest);
    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse response, boolean isSuccess) {
        super.onApiResponseReceive(method, response, isSuccess);
        if(method==ApiMethod.GET_CRYPTO_WALLET){
            GetCryptoWalletResponse getCryptoWalletResponse= (GetCryptoWalletResponse) response;
            handleGetStocksResponse(getCryptoWalletResponse);
        }
    }

    private void handleGetStocksResponse(GetCryptoWalletResponse response) {
        List<CryptoStock> stocks = response.getWallet();
        if (stocks != null) {
            mWalletList.clear();
            mWalletList.addAll(stocks);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallet;
    }
}
