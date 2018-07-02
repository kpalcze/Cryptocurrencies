package com.example.android.crypto.ModelClasses;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Created by K on 2018-04-04.
 */

@Entity
public class CoinSimplified {

    @ColumnInfo(name="last_updated")
    private String lastUpdated;

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

}
