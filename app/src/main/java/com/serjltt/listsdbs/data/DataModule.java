package com.serjltt.listsdbs.data;

import android.content.Context;
import com.serjltt.listsdbs.data.api.GithubService;
import com.serjltt.listsdbs.data.db.LocalDatabase;
import com.serjltt.listsdbs.di.ViewScope;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Provides all service classes.
 *
 * Note 1: Databases need to be closed after the works in being done. Since it's tied to the views
 * lifecycle, we can safely inject it to a view component an let it deal with the consequences.
 *
 * Note 2: Retrofit caches created services, for calling {@linkplain Retrofit#create(Class)}
 * multiple times is not a performance concern.
 *
 * FIXME? Note: This may not be the best solution, and may need refinement.
 */
@ViewScope @Module
public final class DataModule {
  @ViewScope @Provides LocalDatabase provideLocalDatabase(Context context) {
    return LocalDatabase.create(context);
  }

  @ViewScope @Provides GithubService provideGithubService(Retrofit retrofit) {
    return retrofit.create(GithubService.class);
  }
}
