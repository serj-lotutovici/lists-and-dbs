package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.mvi.MviPresenter;
import com.serjltt.listsdbs.repos.usecase.GetReposResult;
import com.serjltt.listsdbs.repos.usecase.GetReposUseCase;
import com.serjltt.listsdbs.rx.RxModule;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import javax.inject.Inject;
import javax.inject.Named;

final class ReposPresenter extends MviPresenter<ReposView> {
  private final GetReposUseCase getReposUseCase;
  private final Scheduler ioScheduler;
  private final Scheduler mainScheduler;

  @Inject ReposPresenter(GetReposUseCase getReposUseCase,
      @Named(RxModule.IO_SCHEDULER) Scheduler ioScheduler,
      @Named(RxModule.MAIN_SCHEDULER) Scheduler mainScheduler) {
    this.getReposUseCase = getReposUseCase;
    this.ioScheduler = ioScheduler;
    this.mainScheduler = mainScheduler;
  }

  @Override protected void onBind(final ReposView view, CompositeDisposable disposable) {
    disposable.add(getReposUseCase.getRepos(1)
        .map(new Function<GetReposResult, ReposUiModel>() {
          @Override public ReposUiModel apply(GetReposResult getReposResult) throws Exception {
            if (getReposResult.isError()) {
              return ReposUiModel.error(1);
            }

            return ReposUiModel.success(getReposResult.body(), 1);
          }
        })
        .subscribeOn(ioScheduler)
        .observeOn(mainScheduler)
        .subscribe(new Consumer<ReposUiModel>() {
          @Override public void accept(ReposUiModel reposUiModel) throws Exception {
            view.render(reposUiModel);
          }
        }));
  }
}
