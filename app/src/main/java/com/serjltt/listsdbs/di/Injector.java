package com.serjltt.listsdbs.di;

import com.serjltt.listsdbs.rx.RxModule;
import dagger.Component;

/** Application global injector. */
@AppScope @Component(
    modules = {
        PlatformModule.class,
        RxModule.class
    }
)
public interface Injector {
}
