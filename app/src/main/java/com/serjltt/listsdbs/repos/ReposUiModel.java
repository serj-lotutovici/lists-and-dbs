package com.serjltt.listsdbs.repos;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.serjltt.listsdbs.data.model.Repository;
import java.util.List;

// TODO Just a place holder atm
@AutoValue abstract class ReposUiModel {
  static ReposUiModel success(List<Repository> data, int page) {
    return new AutoValue_ReposUiModel.Builder()
        .isError(false)
        .data(data)
        .page(page)
        .build();
  }

  static ReposUiModel error(int page) {
    return new AutoValue_ReposUiModel.Builder()
        .isError(true)
        .page(page)
        .build();
  }

  abstract boolean isError();

  @Nullable abstract List<Repository> data();

  abstract int page();

  @AutoValue.Builder static abstract class Builder {
    abstract Builder isError(boolean isError);

    abstract Builder data(@Nullable List<Repository> data);

    abstract Builder page(int page);

    abstract ReposUiModel build();
  }
}
