package com.serjltt.listsdbs.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/** Represents consumer scope (Activities, Fragments, e.t.c.). */
@Scope @Retention(RetentionPolicy.SOURCE)
public @interface ViewScope {
}
