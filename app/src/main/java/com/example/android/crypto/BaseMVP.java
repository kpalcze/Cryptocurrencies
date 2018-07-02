package com.example.android.crypto;

import com.example.android.crypto.Coin.CoinMVP;

/**
 * Created by K on 2018-04-07.
 */

public interface BaseMVP {
    public interface View {
        void showSnackbarError(String msg);
        void showProgress();
        void hideProgress();
        void stopRefreshing();
    }
    public interface Presenter {
    }
}
