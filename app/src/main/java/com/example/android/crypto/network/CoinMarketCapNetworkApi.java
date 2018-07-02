package com.example.android.crypto.network;

import com.example.android.crypto.ModelClasses.Coin;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by K on 2018-03-26.
 */

public interface CoinMarketCapNetworkApi {
    @GET("ticker/?limit=100")
    Observable<List<Coin>> getCoins();

    @GET("ticker/{id}")
    Observable<List<Coin>> getCoinById(@Path("id") String id);

    //https://s2.coinmarketcap.com/static/img/coins/32x32/4.png
}
