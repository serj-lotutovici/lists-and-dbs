package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.di.ViewScope;
import com.serjltt.listsdbs.mvi.MviPresenter;
import dagger.Binds;
import dagger.Module;

@ViewScope @Module
abstract class ReposBindingModule {
  @ViewScope @Binds abstract MviPresenter<ReposView> bindReposPresenter(ReposPresenter presenter);
}
