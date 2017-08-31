package com.serjltt.listsdbs.di;

import dagger.Component;

/** Application global injector. */
@AppScope @Component(
    modules = {
        PlatformModule.class
    }
)
public interface Injector {
}
