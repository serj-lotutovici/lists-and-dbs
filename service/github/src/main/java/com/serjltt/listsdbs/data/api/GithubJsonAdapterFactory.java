package com.serjltt.listsdbs.data.api;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

@MoshiAdapterFactory public abstract class GithubJsonAdapterFactory implements JsonAdapter.Factory {
  public static JsonAdapter.Factory create() {
    return new AutoValueMoshi_GithubJsonAdapterFactory();
  }
}
