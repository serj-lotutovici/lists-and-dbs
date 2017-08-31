package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.mvi.MviPresenter;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

final class ReposPresenter extends MviPresenter<ReposView> {
  @Inject ReposPresenter() {
  }

  @Override protected void onBind(ReposView view, CompositeDisposable disposable) {

  }
}
