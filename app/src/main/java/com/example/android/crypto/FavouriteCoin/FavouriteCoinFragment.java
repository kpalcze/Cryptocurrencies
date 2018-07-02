package com.example.android.crypto.FavouriteCoin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.crypto.Coin.CoinDividerItemDecoration;
import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by K on 2018-04-07.
 */

public class FavouriteCoinFragment extends Fragment implements FavouriteCoinMVP.View {

    @Inject
    FavouriteCoinMVP.Presenter presenter;

    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.place_snackbar)
    ViewGroup rootView;

    private FavouriteCoinAdapter favouriteCoinAdapter;
    private List<Coin> favouriteCoinList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initSwipeRefreshLayout();
        presenter.setView(this);
        downloadFavouriteCoins();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void downloadFavouriteCoins() {
        Map<String, ?> favouriteCoins = sharedPreferences.getAll();
        List<String> listOfFavouriteCoins = new ArrayList<>();

        for (Map.Entry<String, ?> entry : favouriteCoins.entrySet()) {
            System.out.println("map values " + entry.getKey() + ": " + entry.getValue().toString());
            listOfFavouriteCoins.add(entry.getValue().toString());
        }
        presenter.downloadData(listOfFavouriteCoins);
    }

    private void initRecyclerView() {
        favouriteCoinAdapter = new FavouriteCoinAdapter(favouriteCoinList, sharedPreferences);
        recyclerView.setAdapter(favouriteCoinAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new CoinDividerItemDecoration(getActivity()));
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            favouriteCoinList.clear();
            downloadFavouriteCoins();
        });
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void showSnackbarError(String msg) {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateList(Coin coin) {
        favouriteCoinList.add(coin);
        Collections.sort(favouriteCoinList, (coin1, coin2) -> Integer.parseInt(coin1.getRank()) - Integer.parseInt(coin2.getRank()));
        favouriteCoinAdapter.setCoinListData(favouriteCoinList);
        favouriteCoinAdapter.notifyDataSetChanged();
    }
}
