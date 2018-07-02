package com.example.android.crypto.di;

import com.example.android.crypto.Coin.CoinFragment;
import com.example.android.crypto.Coin.CoinFragmentModule;
import com.example.android.crypto.FavouriteCoin.FavouriteCoinFragment;
import com.example.android.crypto.FavouriteCoin.FavouriteCoinFragmentModule;
import com.example.android.crypto.MainActivity;
import com.example.android.crypto.News.NewsFragment;
import com.example.android.crypto.News.NewsFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by K on 2018-04-02.
 */

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = CoinFragmentModule.class)
    abstract CoinFragment bindCoinFragment();

    @ContributesAndroidInjector(modules = NewsFragmentModule.class)
    abstract NewsFragment bindNewsFragment();

    @ContributesAndroidInjector(modules = FavouriteCoinFragmentModule.class)
    abstract FavouriteCoinFragment bindFavouriteCoinFragment();

    // Add bindings for other sub-components here
}
