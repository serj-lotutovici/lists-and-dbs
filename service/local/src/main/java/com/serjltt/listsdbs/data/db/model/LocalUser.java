package com.serjltt.listsdbs.data.db.model;

import android.support.annotation.Nullable;

public final class LocalUser {
  private final String login;
  @Nullable private final String avatarUrl;

  public LocalUser(String login, @Nullable String avatarUrl) {
    this.login = login;
    this.avatarUrl = avatarUrl;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LocalUser localUser = (LocalUser) o;
    return login.equals(localUser.login)
        && (avatarUrl != null ? avatarUrl.equals(localUser.avatarUrl)
        : localUser.avatarUrl == null);
  }

  @Override public int hashCode() {
    int result = login.hashCode();
    result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "LocalUser{" +
        "login='" + login + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        '}';
  }

  public String login() {
    return login;
  }

  @Nullable public String avatarUrl() {
    return avatarUrl;
  }
}
