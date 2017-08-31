package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.di.ViewScope;
import com.serjltt.listsdbs.mvi.MviPresenter;
import com.serjltt.listsdbs.repos.usecase.GetReposUseCase;
import com.serjltt.listsdbs.repos.usecase.RealGetReposUseCase;
import dagger.Binds;
import dagger.Module;

@ViewScope @Module
abstract class ReposBindingModule {
  @ViewScope @Binds abstract MviPresenter<ReposView> bindReposPresenter(ReposPresenter presenter);

  @ViewScope @Binds abstract GetReposUseCase bindGetReposUseCase(RealGetReposUseCase useCase);
}
