package com.example.android.crypto;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.android.crypto.Coin.CoinFragment;
import com.example.android.crypto.Coin.CoinMVP;
import com.example.android.crypto.FavouriteCoin.FavouriteCoinFragment;
import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.News.NewsFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @BindView(R.id.activity_main_root_view)
    ViewGroup rootView;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.item_background));
        initBottomNavigationView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.action_favorites);
        addFragment(new FavouriteCoinFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                Log.d("essa", "1");
                                addFragment(new FavouriteCoinFragment());
                                break;
                            case R.id.action_coins:
                                addFragment(new CoinFragment());
                                break;

                            case R.id.action_news:
                                addFragment(new NewsFragment());
                                break;
                        }
                        return true;
                    }
                });
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
