package com.serjltt.listsdbs.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.serjltt.listsdbs.data.db.model.LocalRepository;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface LocalService {
  /** Observe all repositories. */
  @Query("SELECT * FROM repository")
  Single<List<LocalRepository>> repositories();

  /** Add a new set of repositories. */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void add(List<LocalRepository> repositories);
}
