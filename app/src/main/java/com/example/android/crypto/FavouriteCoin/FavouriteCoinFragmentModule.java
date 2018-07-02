package com.example.android.crypto.FavouriteCoin;

import com.example.android.crypto.network.CoinMarketCapNetworkApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by K on 2018-04-07.
 */

@Module
public class FavouriteCoinFragmentModule {

    @Provides
    static FavouriteCoinMVP.Presenter  provideNewsPresenter(CoinMarketCapNetworkApi api) {
        return new FavouriteCoinPresenter(api);
    }

}
