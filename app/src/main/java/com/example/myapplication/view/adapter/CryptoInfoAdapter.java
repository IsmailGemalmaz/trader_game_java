package com.example.myapplication.view.adapter;

import android.content.Context;
import android.media.MediaCodec;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.view.layout.CryptoInfoListItemLayout;


import java.util.List;

import javax.security.auth.callback.Callback;

import butterknife.BindView;

public class CryptoInfoAdapter extends  RecyclerView.Adapter<CryptoInfoAdapter.ViewHolder>{

    private Context mContext;
    private List<CryptoStock> mCryptoStock;

    public CryptoInfoAdapter(Context context, List<CryptoStock> cryptoStocks ) {
        mContext=context;
        mCryptoStock=cryptoStocks;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CryptoInfoListItemLayout layout=new CryptoInfoListItemLayout(mContext);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        CryptoStock stock=mCryptoStock.get(position);
        holder.layout.fillContent(stock,position);
    }

    @Override
    public int getItemCount() {
        return mCryptoStock.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CryptoInfoListItemLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=(CryptoInfoListItemLayout) itemView;
        }
    }
}
