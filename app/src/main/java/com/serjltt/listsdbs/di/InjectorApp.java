package com.serjltt.listsdbs.di;

import android.app.Application;
import android.content.Context;
import android.support.annotation.CallSuper;

/** Main contract of an application that keeps a reference to the global injector. */
public abstract class InjectorApp extends Application {
  static InjectorApp get(Context context) {
    return (InjectorApp) context.getApplicationContext();
  }

  private Injector injector;

  @CallSuper @Override public void onCreate() {
    super.onCreate();
    injector = buildInjector();
  }

  public Injector injector() {
    return injector;
  }

  protected abstract Injector buildInjector();
}
