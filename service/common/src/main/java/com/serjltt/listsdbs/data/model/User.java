package com.serjltt.listsdbs.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue public abstract class User {
  public static User create(@NonNull String login, @Nullable String avatarUrl) {
    return new AutoValue_User(login, avatarUrl);
  }

  @NonNull public abstract String login();

  @Nullable public abstract String avatarUrl();
}
