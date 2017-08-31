package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.data.DbModule;
import com.serjltt.listsdbs.di.ViewScope;
import dagger.Subcomponent;

@ViewScope @Subcomponent(
    modules = {
        DbModule.class,
        ReposBindingModule.class
    }
)
public interface ReposComponent {
  void inject(ReposActivity activity);
}
