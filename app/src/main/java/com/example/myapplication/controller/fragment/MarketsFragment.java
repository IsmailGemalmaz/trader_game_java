package com.example.myapplication.controller.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.constant.ApiMethod;
import com.example.myapplication.constant.FilterType;
import com.example.myapplication.controller.EventListener;
import com.example.myapplication.controller.base.BaseActivity;
import com.example.myapplication.controller.base.BaseApiActivity;
import com.example.myapplication.controller.base.BaseFragment;
import com.example.myapplication.manager.api.ApiListener;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.model.entity.UserEntity;
import com.example.myapplication.model.request.AddCryptoFavoritesRequest;
import com.example.myapplication.model.request.GetCryptoFavoritesRequest;
import com.example.myapplication.model.request.GetCryptoStockRequest;
import com.example.myapplication.model.response.BaseResponse;
import com.example.myapplication.model.response.GetConfigResponse;
import com.example.myapplication.model.response.GetCryptoStockResponse;
import com.example.myapplication.utility.L;
import com.example.myapplication.view.adapter.CryptoInfoAdapter;
import com.example.myapplication.view.widget.EndlessRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import butterknife.BindView;

public class  MarketsFragment extends BaseFragment implements View.OnClickListener, EventListener, ApiListener {


    @BindView(R.id.rvCryptoİnfo)
    EndlessRecyclerView recyclerView;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.tvUserName)
    TextView tvUserName;

    private CryptoInfoAdapter mAdapter;
    private List<CryptoStock> mStockList;
    private int mCompaniesRequestInterval;
    private FilterType mFilterType;
    private boolean mIsServiceAlreadyCalled;
    private String mPrevSearchText;
    private UserEntity mUser;
    private String mSearchText;
    private BaseApiActivity mActivity;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_markets;
    }

    @Override
    public void assignObjects() {
        super.assignObjects();
        mStockList=new ArrayList<>();
        mAdapter=new CryptoInfoAdapter(context,mStockList);
        mFilterType=FilterType.ALL;
        mActivity=(BaseApiActivity) getContext();
        mUser=mActivity.getApp().getUser();

       // setCompaniesRequestInterval();
    }

    @Override
    public void setListeners() {
        super.setListeners();
        etSearch.addTextChangedListener(mOnTextChangeListener);
    }

    @Override
    public void onEventReceive(int event, Object... datas) {
        super.onEventReceive(event, datas);
        sendStocksRequestByFilterType();

        }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void prepareUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        tvUserName.setText(mUser.getFirstName()+" "+mUser.getLastName());
    }

    @Override
    public void onResumed() {
        super.onResumed();
      //  mSendGetStocksRequestHandler.sendEmptyMessage(0);
    }

    @Override
    public void onPaused() {
        super.onPaused();
        //mSendGetStocksRequestHandler.removeMessages(0);

    }

    @Override
    public void onLayoutReady() {
        super.onLayoutReady();
        sendGetStocksRequest();

    }

    @Override
    public void onApiResponseReceive(ApiMethod method, BaseResponse baseResponse, boolean isSuccess) {
        if (method == ApiMethod.GET_CRYPTO_STOCK ) {
            mIsServiceAlreadyCalled = true;
            GetCryptoStockResponse response = (GetCryptoStockResponse) baseResponse;
            handleGetStocksResponse(response);
        }
    }

    private void sendStocksRequestByFilterType() {
        if (mFilterType == FilterType.ALL) {
            sendGetStocksRequest();
        }
    }

    private void sendGetStocksRequest() {
        GetCryptoStockRequest  request = new GetCryptoStockRequest();
        request.setKey("a8a3452e71305947867f9f04df8fd319");
        sendRequest(request,true);
    }



    private void handleGetStocksResponse(GetCryptoStockResponse response) {
        List<CryptoStock> stocks = response.getStocks();
        if (stocks != null) {
            boolean isAllCompaniesFiltered = (mFilterType == FilterType.ALL);
            mStockList.clear();
            mStockList.addAll(stocks);
            mAdapter.notifyDataSetChanged();
        }

    }


    public  void  sendAddCryptoFavoritesRequest(){
        AddCryptoFavoritesRequest request=new AddCryptoFavoritesRequest();
        request.setCurrency("bitcoin");
        sendRequest(request);
    }

    private void setCompaniesRequestInterval() {
        GetConfigResponse response = getApp().getGetConfigResponse();
        mCompaniesRequestInterval = response.getClientRequestInterval();
        mCompaniesRequestInterval = 1000;
        if (mCompaniesRequestInterval == 0) {
            mCompaniesRequestInterval = 10 * 1000;
        }
    }

    private boolean isOkToSend() {
        if (TextUtils.isEmpty(mPrevSearchText) && !TextUtils.isEmpty(mSearchText)) {
            mPrevSearchText = mSearchText;
            return true;
        } else if (!TextUtils.isEmpty(mPrevSearchText) && !TextUtils.isEmpty(mSearchText)) {
            mPrevSearchText = mSearchText;
            return true;
        } else if (!TextUtils.isEmpty(mPrevSearchText) && TextUtils.isEmpty(mSearchText)) {
            mPrevSearchText = mSearchText;
            return true;
        } else {
            return false;
        }
    }

    private TextWatcher mOnTextChangeListener = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            mSearchText = editable.toString().trim();
            if (isOkToSend()) {
                mSendGetStocksRequestHandler.removeMessages(0);
                mSendGetStocksRequestHandler.sendEmptyMessageDelayed(0, 500);
            }

        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    };


    private Handler mSendGetStocksRequestHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            sendStocksRequestByFilterType();
            mSendGetStocksRequestHandler.sendEmptyMessageDelayed(0, mCompaniesRequestInterval);
        }
    };




}
