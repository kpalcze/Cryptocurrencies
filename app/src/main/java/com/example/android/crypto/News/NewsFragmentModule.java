package com.example.android.crypto.News;

import com.example.android.crypto.network.CryptoCompareNetworkApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by K on 2018-04-02.
 */

@Module
public class NewsFragmentModule {
    @Provides
    static NewsMVP.Presenter  provideNewsPresenter(CryptoCompareNetworkApi api) {
        return new NewsPresenter(api);
    }
}
