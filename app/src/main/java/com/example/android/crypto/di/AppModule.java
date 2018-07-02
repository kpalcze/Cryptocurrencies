package com.example.android.crypto.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.crypto.App;
import com.example.android.crypto.BuildConfig;
import com.example.android.crypto.db.CoinDao;
import com.example.android.crypto.db.Database;
import com.example.android.crypto.network.CoinMarketCapNetworkApi;
import com.example.android.crypto.network.CryptoCompareNetworkApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by K on 2018-04-01.
 */

@Module
public class AppModule {

    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }


    @Provides
    Cache provideOkHttpCache(App application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    public Boolean isNetworkAvailable(App application) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Provides
    public Interceptor provideCacheInterceptor(final boolean isNetworkAvailable) {
            return new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    if (isNetworkAvailable) {
                        Request request = chain.request();

                        CacheControl cacheControl = new CacheControl.Builder()
                                .maxStale(1, TimeUnit.DAYS)
                                .build();

                        request = request.newBuilder()
                                .cacheControl(cacheControl)
                                .build();

                        return chain.proceed(request);
                    } else {
                        Response response = chain.proceed(chain.request());

                        CacheControl cacheControl = new CacheControl.Builder()
                                .maxAge(1, TimeUnit.HOURS)
                                .build();

                        return response.newBuilder()
                                .header("Cache-Control", cacheControl.toString())
                                .build();
                    }
                }
            };
        }

    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache, Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
    }


    @Provides
    public Retrofit provideRetrofitCoinMarketCap(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL_COINMARKETCAP)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.client(okHttpClient)
                .build();
    }

    @Provides
    public Retrofit provideRetrofitCryptoCompare(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL_CRYPTOCOMPARE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.client(okHttpClient)
                .build();
    }

    @Provides
    public CoinMarketCapNetworkApi provideCoinMarketCapApiService(OkHttpClient okHttpClient) {
        return provideRetrofitCoinMarketCap(okHttpClient).create(CoinMarketCapNetworkApi.class);
    }

    @Provides
    public CryptoCompareNetworkApi provideCryptoCompareApiService(OkHttpClient okHttpClient) {
        return provideRetrofitCryptoCompare(okHttpClient).create(CryptoCompareNetworkApi.class);
    }

    @Singleton
    @Provides
    Database providesRoomDatabase(App application) {
        return Database.getInstance(application);
    }

    @Singleton
    @Provides
    CoinDao providesProductDao(Database database) {
        System.out.println("Creating database dao");

        return database.getCoinDao();
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(App application) {
        return application.getApplicationContext().getSharedPreferences("CryptoPreferences", Context.MODE_PRIVATE);
    }


}
