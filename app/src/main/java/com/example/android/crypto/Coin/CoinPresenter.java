package com.example.android.crypto.Coin;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.crypto.BaseMVP;
import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.network.CoinMarketCapNetworkApi;


import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by K on 2018-03-27.
 */

public class CoinPresenter implements CoinMVP.Presenter {

    @Nullable
    private CoinMVP.View view;
    private CoinMVP.Model model;


    public CoinPresenter(CoinMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(CoinMVP.View view) {
        this.view = view;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void loadListOfCoins() {
        if (view != null) {
            view.showProgress();
            model.getCoins()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<Coin>>() {
                        @Override
                        public void onNext(List<Coin> coins) {
                            Log.d("rx2", "onNext");
                            if (coins.isEmpty()) {
                                view.hideProgress();
                            }
                            view.updateList(coins);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("rx2", e.getMessage());
                            view.hideProgress();
                            view.showSnackbarError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.d("rx2", "xxx");
                            view.hideProgress();
                        }
                    });
        }
    }

    @Override
    public void loadListOfCoinsOnlyFromNetwork() {
        if (view != null) {
            view.showProgress();
            model.getCoinsOnlyFromNetwork()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<Coin>>() {
                        @Override
                        public void onNext(List<Coin> coins) {
                            Log.d("rx2", "onNext");
                            //Log.d("rx2 time ", coins.get(0).getLastUpdated());
                            view.updateList(coins);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("rx2", e.getMessage());
                            view.hideProgress();
                            view.showSnackbarError(e.getMessage());
                            view.stopRefreshing();
                        }

                        @Override
                        public void onComplete() {
                            Log.d("rx2", "xxx");
                            view.hideProgress();
                            view.stopRefreshing();
                        }
                    });
        }
    }

}
