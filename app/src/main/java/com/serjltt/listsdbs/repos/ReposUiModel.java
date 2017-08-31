package com.serjltt.listsdbs.repos;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.serjltt.listsdbs.data.model.Repository;
import java.util.List;

@AutoValue abstract class ReposUiModel {
  static ReposUiModel success(List<Repository> data, int page, boolean isOffline) {
    return new AutoValue_ReposUiModel.Builder()
        .isError(false)
        .isOffline(isOffline)
        .data(data)
        .page(page)
        .build();
  }

  static ReposUiModel error(Throwable error, int page) {
    return new AutoValue_ReposUiModel.Builder()
        .isError(true)
        .isOffline(false)
        .error(error)
        .page(page)
        .build();
  }

  static ReposUiModel init() {
    return new AutoValue_ReposUiModel.Builder()
        .isError(false)
        .isOffline(false)
        .page(0)
        .build();
  }

  abstract boolean isError();

  abstract boolean isOffline();

  @Nullable abstract List<Repository> data();

  @Nullable abstract Throwable error();

  abstract int page();

  @AutoValue.Builder static abstract class Builder {
    abstract Builder isError(boolean isError);

    abstract Builder isOffline(boolean isOffline);

    abstract Builder data(@Nullable List<Repository> data);

    abstract Builder page(int page);

    abstract Builder error(Throwable error);

    abstract ReposUiModel build();
  }
}
