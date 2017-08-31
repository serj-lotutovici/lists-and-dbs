package com.serjltt.listsdbs.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@AppScope @Module
public final class PlatformModule {
  private final Context context;

  public PlatformModule(Context context) {
    this.context = context;
  }

  @AppScope @Provides Context provideContext() {
    return context;
  }
}
