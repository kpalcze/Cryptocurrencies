package com.example.android.crypto.FavouriteCoin;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by K on 2018-03-27.
 */

public class FavouriteCoinAdapter extends RecyclerView.Adapter<FavouriteCoinAdapter.ViewHolder> {
    private List<Coin> data;
    private SharedPreferences sharedPreferences;

    public FavouriteCoinAdapter(List<Coin> data, SharedPreferences sharedPreferences) {
        this.data = data;
        this.sharedPreferences = sharedPreferences;
    }

    public void setCoinListData(List<Coin> data) {
        this.data = data;
    }

    @Override
    public FavouriteCoinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FavouriteCoinAdapter.ViewHolder holder, int position) {
        holder.coinRank.setText(data.get(position).getRank());
        holder.coinName.setText(data.get(position).getName());
        holder.coinSymbol.setText("(" + data.get(position).getSymbol() + ")");
        holder.coinPriceUSD.setText(data.get(position).getPriceUsd());
        holder.coinPriceBTC.setText(data.get(position).getPriceBtc());
        String s = data.get(position).getPercentChange1h() + "%";
        if(s.contains("-"))
            holder.coinPriceChange1h.setTextColor(holder.itemView.getResources().getColor(R.color.colorRed));
        else
            holder.coinPriceChange1h.setTextColor(holder.itemView.getResources().getColor(R.color.colorGreen));

        holder.coinPriceChange1h.setText(s);
        s = data.get(position).getPercentChange24h() + "%";
        if(s.contains("-"))
            holder.coinPriceChange24h.setTextColor(holder.itemView.getResources().getColor(R.color.colorRed));
        else
            holder.coinPriceChange24h.setTextColor(holder.itemView.getResources().getColor(R.color.colorGreen));
        holder.coinPriceChange24h.setText(s);

        s = data.get(position).getPercentChange7d() + "%";
        if(s.contains("-"))
            holder.coinPriceChange7d.setTextColor(holder.itemView.getResources().getColor(R.color.colorRed));
        else
            holder.coinPriceChange7d.setTextColor(holder.itemView.getResources().getColor(R.color.colorGreen));
        holder.coinPriceChange7d.setText(s);

        //holder.coinLogo.setImageResource(R.drawable.btc);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(Integer Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.coin_name) TextView coinName;
        @BindView(R.id.coin_symbol) TextView coinSymbol;
        @BindView(R.id.coin_rank) TextView coinRank;
        @BindView(R.id.coin_logo) ImageView coinLogo;
        @BindView(R.id.coin_price_usd) TextView coinPriceUSD;
        @BindView(R.id.coin_price_btc) TextView coinPriceBTC;
        @BindView(R.id.coin_price_change_1h) TextView coinPriceChange1h;
        @BindView(R.id.coin_price_change_24h) TextView coinPriceChange24h;
        @BindView(R.id.coin_price_change_7d) TextView coinPriceChange7d;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.coin_logo)
        void onClick(View view) {
            System.out.println("esssa" + getAdapterPosition());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data.get(getAdapterPosition()).getId(), data.get(getAdapterPosition()).getId());
            editor.apply();
            Map<String, ?> values = sharedPreferences.getAll();
            for (Map.Entry<String, ?> entry : values.entrySet()) {
                System.out.println("map values " + entry.getKey() + ": " + entry.getValue().toString());
            }
        }


        public void click(final Integer cityListData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(cityListData);
                }
            });
        }
    }


}
