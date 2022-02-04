package com.example.myapplication.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.constant.CryptoStockItemType;
import com.example.myapplication.model.entity.CryptoStock;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CryptoDetailListItemLayout extends FrameLayout {

    private static final int RESOURCE = R.layout.layout_crypto_list_item_details;

    @BindView(R.id.tvItemName)
    TextView tvItemName;
    @BindView(R.id.tvItemValue)
    TextView getTvItemValue;

    private Context mContext;
    private CryptoStock mStockValue;

    public CryptoDetailListItemLayout(@NonNull Context context,@Nullable AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(RESOURCE, this, true);
        ButterKnife.bind(this);
        mContext = context;
    }

   public void fillContent(CryptoStockItemType itemType,String value){
        if(CryptoStockItemType.CURRENCY==itemType){
            getTvItemValue.setText(String.valueOf(value));
            tvItemName.setText("İsim");
        }else if(CryptoStockItemType.RANK==itemType){
            getTvItemValue.setText(String.valueOf(value));
            tvItemName.setText("Sıra");
        }else if(CryptoStockItemType.PRICE==itemType){
            getTvItemValue.setText(String.valueOf(value));
            tvItemName.setText("Fiyat");
        }else if(CryptoStockItemType.MARKET_CAP==itemType){
            getTvItemValue.setText(String.valueOf(value));
            tvItemName.setText("Piyasa Değeri");
        }else if(CryptoStockItemType.HİGH==itemType){
                getTvItemValue.setText(value);
            tvItemName.setText("En Yüksek Fiyat");
        }else if(CryptoStockItemType.HİGH_TİMES_TAMP==itemType){
            getTvItemValue.setText(value);
            tvItemName.setText("En Yüksek Zamanı");
            tvItemName.setTextSize(17);
            getTvItemValue.setTextSize(17);
        }else if(CryptoStockItemType.MERKET_CAP_DOMINANCE==itemType){
            getTvItemValue.setText(value);
            tvItemName.setText("Dominance");
        }else if(CryptoStockItemType.MAX_SUPPLY==itemType){
            getTvItemValue.setText(value);
            tvItemName.setText("Max. Arz");
        }else if(CryptoStockItemType.CIRCULATING_SUPPLY==itemType){
            getTvItemValue.setText(value);
            tvItemName.setText("Dolaşan Arz");}

    }
    }
