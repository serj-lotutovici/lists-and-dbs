package com.serjltt.listsdbs.data.db;

import com.serjltt.listsdbs.data.db.model.LocalRepository;
import com.serjltt.listsdbs.data.db.model.LocalUser;
import com.serjltt.listsdbs.data.model.Repository;
import com.serjltt.listsdbs.data.model.User;
import io.reactivex.functions.Function;

public final class LocalMappers {
  public static final Function<LocalRepository, Repository> REPOSITORY_MAPPER =
      new Function<LocalRepository, Repository>() {
        @Override public Repository apply(LocalRepository localRepository) throws Exception {
          return Repository.create(localRepository.name(), localRepository.description(),
              USER_MAPPER.apply(localRepository.owner()));
        }
      };

  public static final Function<LocalUser, User> USER_MAPPER =
      new Function<LocalUser, User>() {
        @Override public User apply(LocalUser localUser) throws Exception {
          return User.create(localUser.login(), localUser.avatarUrl());
        }
      };

  private LocalMappers() {
    throw new AssertionError("No instances.");
  }
}
