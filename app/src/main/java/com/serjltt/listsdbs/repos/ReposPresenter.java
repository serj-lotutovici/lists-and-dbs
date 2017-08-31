package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.mvi.MviPresenter;
import com.serjltt.listsdbs.repos.usecase.GetReposResult;
import com.serjltt.listsdbs.repos.usecase.GetReposUseCase;
import com.serjltt.listsdbs.rx.RxModule;
import io.reactivex.Scheduler;
import io.reactivex.SingleSource;
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
    disposable.add(
        view.state()
            .observeOn(ioScheduler)
            .flatMapSingle(new Function<ReposUiModel, SingleSource<ReposUiModel>>() {
              @Override public SingleSource<ReposUiModel> apply(ReposUiModel reposUiModel)
                  throws Exception {

                final int newPage;
                if (reposUiModel.isOffline()) {
                  newPage = 1; // Each time we are loading from offline, try from first page.
                } else {
                  newPage = reposUiModel.page() + 1; // Increment page.
                }

                return getReposUseCase.getRepos(newPage)
                    .map(new Function<GetReposResult, ReposUiModel>() {
                      @Override public ReposUiModel apply(GetReposResult getReposResult)
                          throws Exception {
                        if (getReposResult.isError()) {
                          return ReposUiModel.error(getReposResult.error(), newPage);
                        }

                        return ReposUiModel.success(getReposResult.body(), newPage,
                            getReposResult.isOffline());
                      }
                    });
              }
            })
            .observeOn(mainScheduler)
            .subscribe(new Consumer<ReposUiModel>() {
              @Override public void accept(ReposUiModel reposUiModel) throws Exception {
                view.render(reposUiModel);
              }
            })
    );
  }
}
