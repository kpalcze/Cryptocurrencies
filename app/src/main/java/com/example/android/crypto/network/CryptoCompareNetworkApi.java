package com.example.android.crypto.network;

import com.example.android.crypto.ModelClasses.News;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by K on 2018-04-02.
 */

public interface CryptoCompareNetworkApi {
    @GET("data/news/")
    Observable<List<News>> getNews();
}