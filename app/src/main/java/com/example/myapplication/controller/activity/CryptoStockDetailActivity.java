package com.example.myapplication.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.CryptoStockItemType;
import com.example.myapplication.constant.FilterType;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.controller.base.BaseApiActivity;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.entity.CryptoStockValue;
import com.example.myapplication.model.request.AddCryptoFavoritesRequest;
import com.example.myapplication.model.request.GetCryptoStockRequest;
import com.example.myapplication.model.request.GetStockDetailRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;
import com.example.myapplication.model.response.GetStockDetailResponse;
import com.example.myapplication.view.adapter.CryptoDetailAdapter;
import com.example.myapplication.view.layout.CryptoDetailListItemLayout;
import com.example.myapplication.view.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CryptoStockDetailActivity extends BaseActivity implements View.OnClickListener  {

    private static final String EXTRA_TITLE_ID = getNewExtraId();
    private static final String EXTRA_STOCK_ID = getNewExtraId();

    @BindView(R.id.ibDetailBack)
    ImageButton ibDetailBack;
    @BindView(R.id.tvCoinName)
    TextView tvCoinName;
    @BindView(R.id.cDlCurrrency)
    CryptoDetailListItemLayout cDlCurrency;
    @BindView(R.id.cDlRank)
    CryptoDetailListItemLayout cDlRank;
    @BindView(R.id.cDlPrice)
    CryptoDetailListItemLayout cDlPrice;
    @BindView(R.id.cDlMarketCap)
    CryptoDetailListItemLayout cDlMarketCap;
    @BindView(R.id.cDlHigh)
    CryptoDetailListItemLayout cDlHigh;
    @BindView(R.id.cDlHighTimesTamp)
    CryptoDetailListItemLayout cDlHighTimesTamp;
    @BindView(R.id.cdlMarketCapDominance)
    CryptoDetailListItemLayout getcDlMarketCapDominance;
    @BindView(R.id.cdlMaxSupply)
    CryptoDetailListItemLayout cdlMaxSupply;
    @BindView(R.id.cDlCirculatingSupply)
    CryptoDetailListItemLayout cDlCirculatingSupply;
    @BindView(R.id.ibStockDetailFavorite)
    ImageButton ibStockDetailFavorite;

    private String mTitle;

    private String mStockId;
   private boolean mIsServiceAlreadyCalled;
   private CryptoDetailAdapter mAdapter;
   private List <CryptoStock> cryptoStocks;

    public static void start(Activity activity,String stockCurrency,String stockİd){
        Intent intent=new Intent(activity,CryptoStockDetailActivity.class);
        intent.putExtra(EXTRA_TITLE_ID,stockCurrency);
        intent.putExtra(EXTRA_STOCK_ID,stockİd);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_crypto_stock_detail;

    }

    @Override
    public void prepareUI() {
        super.prepareUI();
        tvCoinName.setText(mTitle);
    }

    @Override
    public void assignObjects() {
        super.assignObjects();
        mTitle=getIntent().getStringExtra(EXTRA_TITLE_ID);
        mStockId=getIntent().getStringExtra(EXTRA_STOCK_ID);
    }

    @Override
    public void setListeners() {
        super.setListeners();
        ibDetailBack.setOnClickListener(this);
        ibStockDetailFavorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==ibDetailBack){
            onBackPressed();
        }else if(v==ibStockDetailFavorite){
            sendAddCryptoFavoritesRequest();
            Toast.info(context,"Favorilere Eklendi");
        }
    }

    @Override
    public void onLayoutReady() {
        super.onLayoutReady();
        sendGetStockRequest();

    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse baseResponse, boolean isSuccess) {
        if(method==ApiMethod.GET_DETAIL_CRYPTO_STOCK ){
            mIsServiceAlreadyCalled=true;
           GetStockDetailResponse  response=(GetStockDetailResponse) baseResponse;
           handleGetStockDetailResponse(response);
        }

    }

  private void  sendGetStockRequest(){
       GetStockDetailRequest  request=new GetStockDetailRequest();
       request.setKey("a8a3452e71305947867f9f04df8fd319");
       request.setIds(mStockId);
       sendRequest(request);
    }

    private void  sendAddCryptoFavoritesRequest(){
        AddCryptoFavoritesRequest request=new AddCryptoFavoritesRequest();
        request.setCurrency(mTitle);
        sendRequest(request);
    }


    @NonNull
   private void handleGetStockDetailResponse(GetStockDetailResponse response){
        cryptoStocks= response.getStock();

        cDlCurrency.fillContent(CryptoStockItemType.CURRENCY,(cryptoStocks.get(0).getName()+" ("+cryptoStocks.get(0).getCurrency()+")"));
        cDlRank.fillContent(CryptoStockItemType.RANK,cryptoStocks.get(0).getRank());
        cDlPrice.fillContent(CryptoStockItemType.PRICE,String.valueOf(cryptoStocks.get(0).getPrice()));
        cDlMarketCap.fillContent(CryptoStockItemType.MARKET_CAP,cryptoStocks.get(0).getMarket_cap());
        cDlHigh.fillContent(CryptoStockItemType.HİGH,cryptoStocks.get(0).getHigh());
        cDlHighTimesTamp.fillContent(CryptoStockItemType.HİGH_TİMES_TAMP,cryptoStocks.get(0).getHigh_timestamp());
        getcDlMarketCapDominance.fillContent(CryptoStockItemType.MERKET_CAP_DOMINANCE,cryptoStocks.get(0).getMarket_cap_dominance());
        cdlMaxSupply.fillContent(CryptoStockItemType.MAX_SUPPLY,cryptoStocks.get(0).getMax_supply());
        cDlCirculatingSupply.fillContent(CryptoStockItemType.CIRCULATING_SUPPLY,cryptoStocks.get(0).getCirculating_supply());



   }

}
