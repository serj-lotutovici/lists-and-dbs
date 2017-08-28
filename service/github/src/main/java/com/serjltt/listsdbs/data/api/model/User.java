package com.serjltt.listsdbs.data.api.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue public abstract class User {
  public static JsonAdapter<User> jsonAdapter(Moshi moshi) {
    return new AutoValue_User.MoshiJsonAdapter(moshi);
  }

  @VisibleForTesting public static User create(@NonNull String login, @Nullable String avatarUrl) {
    return new AutoValue_User(login, avatarUrl);
  }

  @NonNull public abstract String login();

  @Nullable @Json(name = "avatar_url") public abstract String avatarUrl();
}
