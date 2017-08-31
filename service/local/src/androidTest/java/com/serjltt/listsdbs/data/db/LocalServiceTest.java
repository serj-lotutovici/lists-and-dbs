package com.serjltt.listsdbs.data.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.serjltt.listsdbs.data.db.model.LocalRepository;
import com.serjltt.listsdbs.data.db.model.LocalUser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public final class LocalServiceTest {
  @Rule public final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
  private LocalDatabase localDatabase;

  @Before public void setUp() throws Exception {
    Context context = InstrumentationRegistry.getContext();
    localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabase.class)
        .allowMainThreadQueries()
        .build();
  }

  @After public void tearDown() throws Exception {
    localDatabase.close();
  }

  @Test public void localServicesPersistsRepository() throws Exception {
    LocalUser testUser = new LocalUser("JohnDoe", null);

    List<LocalRepository> repositories = new ArrayList<>();
    repositories.add(new LocalRepository("test1", null, testUser));
    repositories.add(new LocalRepository("test2", "This is a test", testUser));

    LocalService service = localDatabase.service();

    service.add(repositories);

    List<LocalRepository> persisted = service.repositories()
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .values()
        .get(0);

    assertThat(persisted).isEqualTo(repositories);
  }
}
