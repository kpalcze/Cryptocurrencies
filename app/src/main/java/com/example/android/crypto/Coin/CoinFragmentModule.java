package com.example.android.crypto.Coin;

import com.example.android.crypto.db.CoinDao;
import com.example.android.crypto.db.Database;
import com.example.android.crypto.network.CoinMarketCapNetworkApi;
import com.example.android.crypto.repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by K on 2018-04-01.
 */

@Module
public class CoinFragmentModule {
    @Provides
    static CoinMVP.Presenter  provideAuthPresenter(CoinMVP.Model model) {
        return new CoinPresenter(model);
    }

    @Provides
    public CoinMVP.Model provideLoginActivityModel(Repository repository){
        return new CoinModel(repository);
    }

    @Provides
    public Repository provideLoginRepository(CoinMarketCapNetworkApi api, CoinDao coinDao){
        return new CoinRepository(api, coinDao);
    }

}