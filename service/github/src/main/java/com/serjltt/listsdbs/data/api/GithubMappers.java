package com.serjltt.listsdbs.data.api;

import com.serjltt.listsdbs.data.api.model.GithubRepository;
import com.serjltt.listsdbs.data.api.model.GithubUser;
import com.serjltt.listsdbs.data.model.Repository;
import com.serjltt.listsdbs.data.model.User;
import io.reactivex.functions.Function;

/** Contains mapper functions for conversion between api models and models used in the app. */
public final class GithubMappers {
  public static final Function<GithubRepository, Repository> REPOSITORY_MAPPER =
      new Function<GithubRepository, Repository>() {
        @Override public Repository apply(GithubRepository githubRepository) throws Exception {
          return Repository.create(githubRepository.name(), githubRepository.description(),
              USER_MAPPER.apply(githubRepository.owner()));
        }
      };

  public static final Function<GithubUser, User> USER_MAPPER =
      new Function<GithubUser, User>() {
        @Override public User apply(GithubUser githubUser) throws Exception {
          return User.create(githubUser.login(), githubUser.avatarUrl());
        }
      };

  private GithubMappers() {
    throw new AssertionError("No instances.");
  }
}
