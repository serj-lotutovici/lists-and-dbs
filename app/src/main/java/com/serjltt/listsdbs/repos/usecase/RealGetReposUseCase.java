package com.serjltt.listsdbs.repos.usecase;

import com.serjltt.listsdbs.data.api.GithubMappers;
import com.serjltt.listsdbs.data.api.GithubService;
import com.serjltt.listsdbs.data.api.model.GithubRepository;
import com.serjltt.listsdbs.data.db.LocalMappers;
import com.serjltt.listsdbs.data.db.LocalService;
import com.serjltt.listsdbs.data.db.model.LocalRepository;
import com.serjltt.listsdbs.data.model.Repository;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public final class RealGetReposUseCase implements GetReposUseCase {
  private static final int ITEMS_PER_PAGE = 15;

  private final GithubService githubService;
  private final LocalService localService;

  @Inject RealGetReposUseCase(GithubService githubService, LocalService localService) {
    this.githubService = githubService;
    this.localService = localService;
  }

  @Override public Single<GetReposResult> getRepos(int page) {
    return githubService.jakesRepositories(page, ITEMS_PER_PAGE)
        .flatMap(
            new Function<Result<List<GithubRepository>>, SingleSource<List<GithubRepository>>>() {
              @Override
              public Single<List<GithubRepository>> apply(Result<List<GithubRepository>> result)
                  throws Exception {
                if (result.isError()) {
                  //noinspection ConstantConditions
                  return Single.error(result.error());
                }

                Response<List<GithubRepository>> response = result.response();
                //noinspection ConstantConditions
                if (!response.isSuccessful()) {
                  //noinspection ConstantConditions
                  return Single.error(new Exception(response.errorBody().string()));
                }

                //noinspection ConstantConditions
                return Single.just(response.body());
              }
            })
        .flatMap(new Function<List<GithubRepository>, SingleSource<List<Repository>>>() {
          @Override
          public Single<List<Repository>> apply(List<GithubRepository> githubRepositories)
              throws Exception {
            return Observable.fromIterable(githubRepositories)
                .map(GithubMappers.REPOSITORY_MAPPER)
                .toList();
          }
        })
        .doOnSuccess(new Consumer<List<Repository>>() {
          @Override public void accept(List<Repository> repositories) throws Exception {
            Observable.fromIterable(repositories)
                .map(LocalMappers.TO_LOCAL_MAPPER)
                .toList()
                .subscribe(new Consumer<List<LocalRepository>>() {
                  @Override public void accept(List<LocalRepository> localRepositories)
                      throws Exception {
                    localService.add(localRepositories);
                  }
                });
          }
        })
        .map(new Function<List<Repository>, GetReposResult>() {
          @Override public GetReposResult apply(List<Repository> repositories) throws Exception {
            return GetReposResult.success(repositories);
          }
        })
        .onErrorResumeNext(
            localService.repositories()
                .flatMap(
                    new Function<List<LocalRepository>, SingleSource<List<Repository>>>() {
                      @Override
                      public SingleSource<List<Repository>> apply(
                          List<LocalRepository> localRepositories)
                          throws Exception {
                        return Observable.fromIterable(localRepositories)
                            .map(LocalMappers.FROM_LOCAL_MAPPER)
                            .toList();
                      }
                    })
                .map(new Function<List<Repository>, GetReposResult>() {
                  @Override public GetReposResult apply(List<Repository> repositories)
                      throws Exception {
                    return GetReposResult.offline(repositories);
                  }
                })
        )
        .onErrorReturn(new Function<Throwable, GetReposResult>() {
          @Override public GetReposResult apply(Throwable throwable) throws Exception {
            return GetReposResult.error(throwable);
          }
        });
  }
}
