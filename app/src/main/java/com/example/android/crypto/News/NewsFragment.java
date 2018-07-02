package com.example.android.crypto.News;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.android.crypto.Coin.CoinAdapter;
import com.example.android.crypto.Coin.CoinDividerItemDecoration;
import com.example.android.crypto.ModelClasses.News;
import com.example.android.crypto.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by K on 2018-04-02.
 */

public class NewsFragment extends Fragment implements NewsMVP.View {

    @Inject
    NewsMVP.Presenter presenter;

    @BindView(R.id.recyclerView_news)
    RecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsAdapter newsListAdapter;
    private List<News> newsList = new ArrayList<>();
    private DividerItemDecoration dividerItemDecoration;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initSwipeToRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadListOfNews();
    }

    private void initRecyclerView() {
        newsListAdapter = new NewsAdapter(Glide.with(this), newsList);
        recyclerView.setAdapter(newsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new CoinDividerItemDecoration(getActivity()));
    }

    private void initSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            //presenter.loadListOfNewsOnlyFromNetwork();
        });

    }

    @Override
    public void showSnackbarError(String msg) {

    }

    @Override
    public void updateList(List<News> result) {
        newsListAdapter.setNewsListData(result);
        newsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
