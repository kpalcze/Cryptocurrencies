package com.example.android.crypto.Coin;

import android.util.Log;

import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.db.CoinDao;
import com.example.android.crypto.network.CoinMarketCapNetworkApi;
import com.example.android.crypto.repository.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by K on 2018-04-02.
 */

public class CoinRepository implements Repository {

    private CoinMarketCapNetworkApi api;

    private final CoinDao coinDao;


    public CoinRepository(CoinMarketCapNetworkApi api, CoinDao coinDao) {
        System.out.println("Creating CoinRepository");
        this.api = api;
        this.coinDao = coinDao;
    }

    @Override
    public Observable<List<Coin>> getCoinResults() {

       // long lastModified = Long.parseLong(coinDao.getCoinLastUpdated("Bitcoin").getLastUpdated());
        long currentTime = System.currentTimeMillis() / 1000;
        long timestamp;
        //System.out.println("Last modified:" +  lastModified);
        System.out.println("Current:" +  currentTime);


        System.out.println("getting coin results");

        getCoinResultsFromDatabase().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coins -> {
                    if (!coins.isEmpty())
                        System.out.println("timestamp:" + coins.get(0).getLastUpdated());
                       // if (currentTime - Long.parseLong(coins.get(0).getLastUpdated()) > 65);
                    else
                        System.out.println("empty list");

                    }
                );

        /*
        return Observable.merge(getCoinResultsFromDatabase().toObservable()
                .onErrorResumeNext(observer -> {
                    getCoinResultsFromNetwork();
                }), getCoinResultsFromNetwork()); */

        return getCoinResultsFromNetwork()
                .publish(networkResponse ->  Observable.merge(networkResponse, getCoinResultsFromDatabase().toObservable().takeUntil(networkResponse)))
                .onErrorResumeNext(observerNetworkError -> {
                        getCoinResultsFromDatabase().toObservable();
                });

        /*
        return  getCoinResultsFromDatabase().toObservable()
                .onErrorResumeNext(observer -> {
                    getCoinResultsFromNetwork();
                });*/
    }

    @Override
    public Observable<List<Coin>> getCoinResultsFromNetwork() {
        System.out.println("getting results from network");
        return api.getCoins().doOnNext(coins -> {
            System.out.println("inserting to db");
            coinDao.deleteAll();
            coinDao.insert(coins);
        });
    }

    @Override
    public Single<List<Coin>> getCoinResultsFromDatabase() {
        System.out.println("getting coins from database");
        return coinDao.getAllCoins();
    }

    private Observable<List<Coin>> getEmptyObservable() {
        return Observable.empty();
    }
}
