package com.example.android.crypto.repository;

import com.example.android.crypto.ModelClasses.Coin;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by K on 2018-04-02.
 */

public interface Repository {

    Observable<List<Coin>> getCoinResults();
    Observable<List<Coin>> getCoinResultsFromNetwork();
    Single<List<Coin>> getCoinResultsFromDatabase();


}
