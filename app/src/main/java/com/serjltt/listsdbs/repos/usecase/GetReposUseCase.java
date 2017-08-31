package com.serjltt.listsdbs.repos.usecase;

import io.reactivex.Single;

/** Retrieves a list of repositories from a selected data source. */
public interface GetReposUseCase {
  Single<GetReposResult> getRepos(int page);
}
