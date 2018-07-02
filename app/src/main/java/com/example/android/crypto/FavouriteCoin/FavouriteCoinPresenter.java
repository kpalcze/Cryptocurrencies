package com.example.android.crypto.FavouriteCoin;

import android.support.annotation.Nullable;

import com.example.android.crypto.BaseMVP;
import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.network.CoinMarketCapNetworkApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by K on 2018-04-07.
 */

public class FavouriteCoinPresenter implements FavouriteCoinMVP.Presenter {

    @Nullable
    private FavouriteCoinMVP.View view;
    private FavouriteCoinMVP.Model model;

    private CoinMarketCapNetworkApi api;

    public FavouriteCoinPresenter(CoinMarketCapNetworkApi api) {
        this.api = api;
    }

    @Override
    public void setView(FavouriteCoinMVP.View view) {
        this.view = view;
    }

    @Override
    public void downloadData(List<String> favouriteCoins) {
        if (view != null) {
            view.showProgress();
            List<Observable<List<Coin>>> results = new ArrayList<>();
            for (String c : favouriteCoins) {
                results.add(api.getCoinById(c));
            }
            Observable.zip(
                    results,
                    new Function<Object[], Object>() {
                        @Override
                        public Object apply(Object[] objects) throws Exception {
                            // Objects[] is an array of combined results of completed requests

                            // do something with those results and emit new event
                            return new Object();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    // After all requests had been performed the next observer will receive the Object, returned from Function
                    .subscribe(
                            // Will be triggered if all requests will end successfully (4xx and 5xx also are successful requests too)
                            new Consumer<Object>() {
                                @Override
                                public void accept(Object o) throws Exception {
                                    //Do something on successful completion of all requests
                                }
                            },

                            // Will be triggered if any error during requests will happen
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable e) throws Exception {
                                    //Do something on error completion of requests
                                }
                            }
                    );
            api.getCoinById(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<Coin>>() {
                        @Override
                        public void onNext(List<Coin> coins) {
                            if (!coins.isEmpty())
                                if(coins.size() == 1)
                                    view.updateList(coins.get(0));
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println(e);
                            view.hideProgress();
                            view.stopRefreshing();
                            view.showSnackbarError(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            System.out.println("rx3 compelted");
                            view.hideProgress();
                            view.stopRefreshing();
                        }
                    });
        }
    }
}
