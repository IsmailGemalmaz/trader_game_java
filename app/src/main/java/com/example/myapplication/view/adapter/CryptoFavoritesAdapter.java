package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.entity.CryptoFavoritesStcok;
import com.example.myapplication.model.entity.CryptoStock;
import com.example.myapplication.view.layout.CryptoDetailListItemLayout;
import com.example.myapplication.view.layout.CryptoFavoritesItemLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CryptoFavoritesAdapter extends RecyclerView.Adapter<CryptoFavoritesAdapter.ViewHolder> {


    private Context mContext;
    private List<CryptoFavoritesStcok> mCryptoStock;

    public CryptoFavoritesAdapter(Context mContext, List<CryptoFavoritesStcok> mCryptoStock) {
        this.mContext = mContext;
        this.mCryptoStock = mCryptoStock;
    }

    @NonNull
    @NotNull
    @Override
    public CryptoFavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CryptoFavoritesItemLayout cryptoFavoritesItemLayout=new CryptoFavoritesItemLayout(mContext);
        return new ViewHolder(cryptoFavoritesItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CryptoFavoritesAdapter.ViewHolder holder, int position) {
        CryptoFavoritesStcok stock=mCryptoStock.get(position);
        holder.layout.fillContent(stock,position);
    }

    @Override
    public int getItemCount() {
        return mCryptoStock.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CryptoFavoritesItemLayout layout;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            layout=(CryptoFavoritesItemLayout) itemView;
        }
    }
}
