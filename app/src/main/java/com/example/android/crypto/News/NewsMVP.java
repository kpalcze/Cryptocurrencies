package com.example.android.crypto.News;

import com.example.android.crypto.ModelClasses.News;

import java.util.List;

/**
 * Created by K on 2018-04-02.
 */

public interface NewsMVP {
    interface View {
        void showSnackbarError(String msg);
        void updateList(List<News> result);
        void showProgress();
        void hideProgress();
    }

    interface Presenter {
        void attachView();
        void detachView();
        void loadListOfNews();
        void setView(View view);
    }

    interface Model {

    }
}