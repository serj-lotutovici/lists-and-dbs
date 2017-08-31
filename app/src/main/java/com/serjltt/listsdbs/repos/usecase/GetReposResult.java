package com.serjltt.listsdbs.repos.usecase;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.serjltt.listsdbs.data.model.Repository;
import java.util.List;

@AutoValue public abstract class GetReposResult {
  static Builder builder() {
    return new AutoValue_GetReposResult.Builder();
  }

  public abstract boolean isError();

  public abstract boolean hasOffline();

  @Nullable public abstract List<Repository> body();

  @Nullable public abstract Throwable error();

  @AutoValue.Builder public static abstract class Builder {
    public abstract Builder isError(boolean isError);

    public abstract Builder hasOffline(boolean isOffline);

    public abstract Builder body(@Nullable List<Repository> body);

    public abstract Builder error(@Nullable Throwable error);

    public abstract GetReposResult build();
  }
}
