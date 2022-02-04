package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.view.layout.WalletItemLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CryptoWalletItemAdapter  extends RecyclerView.Adapter<CryptoWalletItemAdapter.ViewHolder> {

    private Context mContext;
    private List<CryptoStock> mCryptoStock;

    public CryptoWalletItemAdapter(Context mContext, List<CryptoStock> mCryptoStock) {
        this.mContext = mContext;
        this.mCryptoStock = mCryptoStock;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        WalletItemLayout walletItemLayout=new WalletItemLayout(mContext);
        return new ViewHolder(walletItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CryptoWalletItemAdapter.ViewHolder holder, int position) {
        CryptoStock stock=mCryptoStock.get(position);
        holder.layout.fillContent(stock,position);
    }

    @Override
    public int getItemCount() {
        return mCryptoStock.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private WalletItemLayout layout;
        public ViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);
            layout= (WalletItemLayout) itemView;
        }

    }
}
