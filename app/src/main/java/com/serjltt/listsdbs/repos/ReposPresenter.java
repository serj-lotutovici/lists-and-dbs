package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.mvi.MviPresenter;
import com.serjltt.listsdbs.repos.usecase.GetReposUseCase;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

final class ReposPresenter extends MviPresenter<ReposView> {
  private final GetReposUseCase getReposUseCase;

  @Inject ReposPresenter(GetReposUseCase getReposUseCase) {
    this.getReposUseCase = getReposUseCase;
  }

  @Override protected void onBind(ReposView view, CompositeDisposable disposable) {
  }
}
