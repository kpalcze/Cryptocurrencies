package com.example.android.crypto.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by K on 2018-04-01.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}