package com.serjltt.listsdbs.data.api.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue public abstract class Repository {
  public static JsonAdapter<Repository> jsonAdapter(Moshi moshi) {
    return new AutoValue_Repository.MoshiJsonAdapter(moshi);
  }

  @VisibleForTesting
  public static Repository create(@NonNull String name, @Nullable String description,
      @NonNull User user) {
    return new AutoValue_Repository(name, user, description);
  }

  @NonNull public abstract String name();

  @NonNull public abstract User owner();

  @Nullable public abstract String description();
}
