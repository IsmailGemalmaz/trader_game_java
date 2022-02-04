package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.FilterType;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.model.entity.CryptoFavoritesStcok;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.request.GetCryptoFavoritesRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetCryptoFavoritesResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;
import com.example.myapplication.view.adapter.CryptoFavoritesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FavoriteActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ibFavoriteBack)
    ImageButton ibFavoriteBack;
    @BindView(R.id.rvFavorites)
    RecyclerView rvFavorites;

   private CryptoFavoritesAdapter cryptoFavoritesAdapter;
    private List<CryptoFavoritesStcok> cryptoStocks;


    @Override
    public int getLayoutId() {
        return R.layout.activity_favorite;

    }

    @Override
    public void onLayoutReady() {
        super.onLayoutReady();
        sendGetFavoritesRequest();
    }

    @Override
    public void assignObjects() {
        super.assignObjects();
        cryptoStocks=new ArrayList<>();
        cryptoFavoritesAdapter=new CryptoFavoritesAdapter(context,cryptoStocks);
    }

    @Override
    public void prepareUI() {
        super.prepareUI();

        rvFavorites.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        rvFavorites.setAdapter(cryptoFavoritesAdapter);
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

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse baseResponse, boolean isSuccess) {
        super.onApiResponseReceive(method, baseResponse, isSuccess);
        if (method == ApiMethod.GET_CRYPTO_FAVORİTES ) {
          GetCryptoFavoritesResponse response;
            response = (GetCryptoFavoritesResponse) baseResponse;
            handleGetStocksResponse(response);
        }
    }

    private void sendGetFavoritesRequest(){
        GetCryptoFavoritesRequest request=new GetCryptoFavoritesRequest();
        sendRequest(request,true);
    }

    private void handleGetStocksResponse(GetCryptoFavoritesResponse response) {
        List<CryptoFavoritesStcok> stocks = response.getCryptoStocks();
        if (stocks != null) {
            cryptoStocks.clear();
            cryptoStocks.addAll(stocks);
            cryptoFavoritesAdapter.notifyDataSetChanged();
        }

    }



}
