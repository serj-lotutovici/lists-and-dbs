package com.serjltt.listsdbs.data.api.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue public abstract class GithubUser {
  public static JsonAdapter<GithubUser> jsonAdapter(Moshi moshi) {
    return new AutoValue_GithubUser.MoshiJsonAdapter(moshi);
  }

  @VisibleForTesting public static GithubUser create(@NonNull String login, @Nullable String avatarUrl) {
    return new AutoValue_GithubUser(login, avatarUrl);
  }

  @NonNull public abstract String login();

  @Nullable @Json(name = "avatar_url") public abstract String avatarUrl();
}
