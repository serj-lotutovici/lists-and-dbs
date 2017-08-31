package com.serjltt.listsdbs;

import com.serjltt.listsdbs.di.DaggerInjector;
import com.serjltt.listsdbs.di.Injector;
import com.serjltt.listsdbs.di.InjectorApp;
import com.serjltt.listsdbs.di.PlatformModule;
import com.serjltt.listsdbs.rx.RxModule;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class ListsDbsApp extends InjectorApp {
  @Override protected Injector buildInjector() {
    return DaggerInjector.builder()
        .platformModule(new PlatformModule(this))
        .rxModule(new RxModule(Schedulers.io(), AndroidSchedulers.mainThread()))
        .build();
  }
}
