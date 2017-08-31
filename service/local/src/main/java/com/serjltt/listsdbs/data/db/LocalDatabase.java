package com.serjltt.listsdbs.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.serjltt.listsdbs.data.db.model.LocalRepository;

@Database(entities = { LocalRepository.class }, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
  /** Returns a new instance of {@linkplain LocalDatabase}. */
  public static LocalDatabase create(Context context) {
    return Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "local.db")
        .build();
  }

  public abstract LocalService service();
}
