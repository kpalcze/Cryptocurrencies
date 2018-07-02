package com.example.android.crypto.News;

import android.util.Log;

import com.example.android.crypto.ModelClasses.News;
import com.example.android.crypto.network.CryptoCompareNetworkApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by K on 2018-04-02.
 */

public class NewsPresenter implements NewsMVP.Presenter {

    private NewsMVP.View view;
    private NewsMVP.Model model;
    private CryptoCompareNetworkApi api;

    public NewsPresenter(CryptoCompareNetworkApi api) {
        this.api = api;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void loadListOfNews() {
        api.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<News>>() {
                    @Override
                    public void onNext(List<News> news) {
                        view.updateList(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("rx2 news", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("rx2 news", "xxx");
                    }
                });
    }

    @Override
    public void setView(NewsMVP.View view) {
        this.view = view;
    }
}
