package com.serjltt.listsdbs.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/** Represents application global scope. */
@Scope @Retention(RetentionPolicy.SOURCE)
public @interface AppScope {
}
