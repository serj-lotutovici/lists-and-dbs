package com.serjltt.listsdbs;

import com.serjltt.listsdbs.data.NetworkModule;
import com.serjltt.listsdbs.di.DaggerInjector;
import com.serjltt.listsdbs.di.Injector;
import com.serjltt.listsdbs.di.InjectorApp;
import com.serjltt.listsdbs.di.PlatformModule;
import com.serjltt.listsdbs.rx.RxModule;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;

public final class ListsDbsApp extends InjectorApp {
  private static final HttpUrl API_ENDPOINT =
      HttpUrl.parse("https://api.github.com");

  @Override protected Injector buildInjector() {
    return DaggerInjector.builder()
        .platformModule(new PlatformModule(this))
        .rxModule(new RxModule(Schedulers.io(), AndroidSchedulers.mainThread()))
        .networkModule(new NetworkModule(API_ENDPOINT))
        .build();
  }
}
