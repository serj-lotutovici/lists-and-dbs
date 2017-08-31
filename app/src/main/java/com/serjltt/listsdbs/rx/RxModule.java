package com.serjltt.listsdbs.rx;

import com.serjltt.listsdbs.di.AppScope;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import javax.inject.Named;

@AppScope @Module
public final class RxModule {
  public static final String IO_SCHEDULER = "io";
  public static final String MAIN_SCHEDULER = "main";

  private final Scheduler ioScheduler;
  private final Scheduler mainThreadScheduler;

  public RxModule(Scheduler ioScheduler, Scheduler mainThreadScheduler) {
    this.ioScheduler = ioScheduler;
    this.mainThreadScheduler = mainThreadScheduler;
  }

  @Provides @Named(IO_SCHEDULER) Scheduler provideIoScheduler() {
    return ioScheduler;
  }

  @Provides @Named(MAIN_SCHEDULER) Scheduler provideMainThreadScheduler() {
    return mainThreadScheduler;
  }
}
