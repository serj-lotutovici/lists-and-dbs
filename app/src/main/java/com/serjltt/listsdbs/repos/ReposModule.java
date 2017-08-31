package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.data.db.LocalDatabase;
import com.serjltt.listsdbs.data.db.LocalService;
import com.serjltt.listsdbs.di.ViewScope;
import dagger.Module;
import dagger.Provides;

@ViewScope @Module
public final class ReposModule {
  @ViewScope @Provides LocalService provideLocalService(LocalDatabase database) {
    return database.service();
  }
}
