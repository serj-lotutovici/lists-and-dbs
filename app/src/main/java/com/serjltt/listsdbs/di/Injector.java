package com.serjltt.listsdbs.di;

import com.serjltt.listsdbs.data.NetworkModule;
import com.serjltt.listsdbs.repos.ReposComponent;
import com.serjltt.listsdbs.rx.RxModule;
import dagger.Component;

/** Application global injector. */
@AppScope @Component(
    modules = {
        PlatformModule.class,
        RxModule.class,
        NetworkModule.class
    }
)
public interface Injector {
  /**
   * Returns an instance of {@linkplain ReposComponent}.
   *
   * In a real life example, this would have been provided via a binding module with component
   * builders.
   */
  ReposComponent reposComponent();
}
