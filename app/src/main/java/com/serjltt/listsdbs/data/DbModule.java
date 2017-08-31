package com.serjltt.listsdbs.data;

import android.content.Context;
import com.serjltt.listsdbs.data.db.LocalDatabase;
import com.serjltt.listsdbs.di.ViewScope;
import dagger.Module;
import dagger.Provides;

/**
 * Databases need to be closed after the works in being done. Since it's tied to the views
 * lifecycle,
 * we can safely inject it to a view component an let it deal with the consequences.
 *
 * FIXME? Note: This may not be the best solution, and may need refinement.
 */
@ViewScope @Module
public final class DbModule {
  @ViewScope @Provides LocalDatabase provideLocalDatabase(Context context) {
    return LocalDatabase.create(context);
  }
}
