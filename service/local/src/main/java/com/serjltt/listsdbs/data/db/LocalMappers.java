package com.serjltt.listsdbs.data.db;

import com.serjltt.listsdbs.data.db.model.LocalRepository;
import com.serjltt.listsdbs.data.db.model.LocalUser;
import com.serjltt.listsdbs.data.model.Repository;
import com.serjltt.listsdbs.data.model.User;
import io.reactivex.functions.Function;

public final class LocalMappers {
  public static final Function<LocalRepository, Repository> FROM_LOCAL_MAPPER =
      new Function<LocalRepository, Repository>() {
        @Override public Repository apply(LocalRepository localRepository) throws Exception {
          return Repository.create(localRepository.name(), localRepository.description(),
              USER_MAPPER.apply(localRepository.owner()));
        }
      };

  public static final Function<Repository, LocalRepository> TO_LOCAL_MAPPER =
      new Function<Repository, LocalRepository>() {
        @Override public LocalRepository apply(Repository repository) throws Exception {
          return new LocalRepository(repository.name(), repository.description(),
              USER_MAPPER_2.apply(repository.owner()));
        }
      };

  private static final Function<LocalUser, User> USER_MAPPER =
      new Function<LocalUser, User>() {
        @Override public User apply(LocalUser localUser) throws Exception {
          return User.create(localUser.login(), localUser.avatarUrl());
        }
      };

  private static final Function<User, LocalUser> USER_MAPPER_2 =
      new Function<User, LocalUser>() {
        @Override public LocalUser apply(User user) throws Exception {
          return new LocalUser(user.login(), user.avatarUrl());
        }
      };

  private LocalMappers() {
    throw new AssertionError("No instances.");
  }
}
