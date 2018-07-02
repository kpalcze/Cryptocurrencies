package com.example.android.crypto.Coin;

import com.example.android.crypto.BaseMVP;
import com.example.android.crypto.ModelClasses.Coin;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;


/**
 * Created by K on 2018-03-27.
 */

public interface CoinMVP {
    interface View extends BaseMVP.View{
        void updateList(List<Coin> result);
    }

    interface Presenter extends BaseMVP.Presenter{
        void attachView();
        void detachView();
        void loadListOfCoins();
        void loadListOfCoinsOnlyFromNetwork();
        void setView(View view);
    }

    interface Model {
        Observable<List<Coin>> getCoins();
        Observable<List<Coin>> getCoinsOnlyFromNetwork();
    }
}