package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.data.DataModule;
import com.serjltt.listsdbs.di.ViewScope;
import dagger.Subcomponent;

@ViewScope @Subcomponent(
    modules = {
        DataModule.class,
        ReposModule.class,
        ReposBindingModule.class
    }
)
public interface ReposComponent {
  void inject(ReposActivity activity);
}
