package com.serjltt.listsdbs.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue public abstract class Repository {
  public static Repository create(@NonNull String name, @Nullable String description,
      @NonNull User user) {
    return new AutoValue_Repository(name, user, description);
  }

  @NonNull public abstract String name();

  @NonNull public abstract User owner();

  @Nullable public abstract String description();
}
