package com.serjltt.listsdbs.mvi;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/** Base contract for a Presenter in a MVI architecture. */
public abstract class MviPresenter<V extends MviView<?>> {
  public final Disposable bind(V view) {
    final CompositeDisposable disposable = new CompositeDisposable();
    onBind(view, disposable);
    return disposable;
  }

  /** Called when this presenters {@linkplain MviView view} binds to it. */
  protected abstract void onBind(V view, CompositeDisposable disposable);
}
