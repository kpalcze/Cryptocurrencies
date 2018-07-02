package com.example.android.crypto.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by K on 2018-04-01.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {}
