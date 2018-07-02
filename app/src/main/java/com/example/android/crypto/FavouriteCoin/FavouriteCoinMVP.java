package com.example.android.crypto.FavouriteCoin;

import com.example.android.crypto.BaseMVP;
import com.example.android.crypto.ModelClasses.Coin;

import java.util.List;

/**
 * Created by K on 2018-04-07.
 */

public interface FavouriteCoinMVP {
    public interface View extends BaseMVP.View {
        public void updateList(Coin coin);
    }
    public interface Presenter extends BaseMVP.Presenter {
        void downloadData(List<String> favouriteCoins);
        void setView(FavouriteCoinMVP.View view);
    }
    public interface Model {

    }
}
