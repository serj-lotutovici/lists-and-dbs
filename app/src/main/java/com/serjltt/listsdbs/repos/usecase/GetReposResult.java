package com.serjltt.listsdbs.repos.usecase;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.serjltt.listsdbs.data.model.Repository;
import java.util.List;

@AutoValue public abstract class GetReposResult {
  private static Builder builder() {
    return new AutoValue_GetReposResult.Builder();
  }

  public static GetReposResult success(List<Repository> repositories) {
    return builder()
        .isOffline(false)
        .isError(false)
        .body(repositories)
        .build();
  }

  public static GetReposResult offline(List<Repository> repositories) {
    return builder()
        .isOffline(true)
        .isError(false)
        .body(repositories)
        .build();
  }

  public static GetReposResult error(Throwable error) {
    return builder()
        .isError(true)
        .isOffline(false)
        .error(error)
        .build();
  }

  public abstract boolean isError();

  public abstract boolean isOffline();

  @Nullable public abstract List<Repository> body();

  @Nullable public abstract Throwable error();

  @AutoValue.Builder public static abstract class Builder {
    public abstract Builder isError(boolean isError);

    public abstract Builder isOffline(boolean isOffline);

    public abstract Builder body(@Nullable List<Repository> body);

    public abstract Builder error(@Nullable Throwable error);

    public abstract GetReposResult build();
  }
}
