package com.example.android.crypto.Coin;

import android.util.Log;

import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.repository.Repository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;


/**
 * Created by K on 2018-03-28.
 */

public class CoinModel implements CoinMVP.Model {

    private Repository repository;

    public CoinModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<Coin>> getCoins() {
        return repository.getCoinResults();
    }

    @Override
    public Observable<List<Coin>> getCoinsOnlyFromNetwork() {
        return repository.getCoinResultsFromNetwork();
    }
}
