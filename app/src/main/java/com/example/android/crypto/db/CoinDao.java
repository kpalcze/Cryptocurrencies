package com.example.android.crypto.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.crypto.ModelClasses.Coin;
import com.example.android.crypto.ModelClasses.CoinSimplified;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by K on 2018-04-03.
 */

@Dao
public interface CoinDao {
    @Query("SELECT * FROM coin")
    Single<List<Coin>> getAllCoins();

    /*
    @Query("SELECT last_updated FROM coin WHERE name LIKE :name")
    CoinSimplified getCoinLastUpdated(String name);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Coin> coins);

    @Update
    void update(List<Coin> coins);

    @Query("DELETE FROM coin")
    void deleteAll();
}
