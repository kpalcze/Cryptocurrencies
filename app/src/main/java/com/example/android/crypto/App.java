package com.example.android.crypto;

import android.app.Activity;
import android.app.Application;


import com.example.android.crypto.di.DaggerAppComponent;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Created by K on 2018-03-26.
 */


public class App extends Application implements HasActivityInjector {

        @Inject
        DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

        @Override
        public void onCreate() {
                super.onCreate();

                Stetho.initializeWithDefaults(this);

                DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
                }

        @Override
        public AndroidInjector<Activity> activityInjector() {
                return dispatchingAndroidInjector;
        }
}
