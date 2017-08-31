package com.serjltt.listsdbs;

import com.serjltt.listsdbs.di.DaggerInjector;
import com.serjltt.listsdbs.di.Injector;
import com.serjltt.listsdbs.di.InjectorApp;
import com.serjltt.listsdbs.di.PlatformModule;

public final class ListsDbsApp extends InjectorApp {
  @Override protected Injector buildInjector() {
    return DaggerInjector.builder()
        .platformModule(new PlatformModule(this))
        .build();
  }
}
