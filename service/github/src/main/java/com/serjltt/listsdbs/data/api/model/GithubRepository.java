package com.serjltt.listsdbs.data.api.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue public abstract class GithubRepository {
  public static JsonAdapter<GithubRepository> jsonAdapter(Moshi moshi) {
    return new AutoValue_GithubRepository.MoshiJsonAdapter(moshi);
  }

  @VisibleForTesting
  public static GithubRepository create(@NonNull String name, @Nullable String description,
      @NonNull GithubUser githubUser) {
    return new AutoValue_GithubRepository(name, githubUser, description);
  }

  @NonNull public abstract String name();

  @NonNull public abstract GithubUser owner();

  @Nullable public abstract String description();
}
