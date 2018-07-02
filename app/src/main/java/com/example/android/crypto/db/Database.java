package com.example.android.crypto.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.crypto.ModelClasses.Coin;

/**
 * Created by K on 2018-04-03.
 */

@android.arch.persistence.room.Database(entities = { Coin.class }, version = 3)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

    public static Database getInstance(Context context) {
        System.out.println("Creating database");
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CoinDao getCoinDao();
}