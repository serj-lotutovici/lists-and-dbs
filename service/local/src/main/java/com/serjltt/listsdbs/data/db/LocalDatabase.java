package com.serjltt.listsdbs.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import com.serjltt.listsdbs.data.db.model.LocalRepository;

@Database(entities = { LocalRepository.class }, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
  public abstract LocalService service();

  /** Simple builder class that provides a workable instance of {@linkplain LocalDatabase}. */
  private final class Builder {
    private final Context context;

    public Builder(@NonNull Context context) {
      this.context = context;
    }

    public LocalDatabase build() {
      return Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "local.db")
          .build();
    }
  }
}
