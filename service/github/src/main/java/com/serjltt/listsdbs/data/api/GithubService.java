package com.serjltt.listsdbs.data.api;

import com.serjltt.listsdbs.data.api.model.Repository;
import io.reactivex.Single;
import java.util.List;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubService {
  @GET("users/JakeWharton/repos") Single<Result<List<Repository>>> jakesRepositories(
      @Query("page") int page, @Query("per_page") int perPage);
}
