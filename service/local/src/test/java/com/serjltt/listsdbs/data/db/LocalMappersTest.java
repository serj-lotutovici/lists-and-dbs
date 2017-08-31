package com.serjltt.listsdbs.data.db;

import com.serjltt.listsdbs.data.db.model.LocalRepository;
import com.serjltt.listsdbs.data.db.model.LocalUser;
import com.serjltt.listsdbs.data.model.Repository;
import com.serjltt.listsdbs.data.model.User;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Same as GithubMappersTest.
 */
public final class LocalMappersTest {
  @Test public void mapsApiModelToCommonModel() throws Exception {
    final String login = "C'Tuh";
    final String avatar = "localhost:8080";
    final String repoName = "local-repo";
    final String description = "This is a test";

    LocalUser inputUser = new LocalUser(login, avatar);
    LocalRepository inputRepo = new LocalRepository(repoName, description, inputUser);

    User expectedUser = User.create(login, avatar);
    Repository expectedRepo = Repository.create(repoName, description, expectedUser);

    assertThat(LocalMappers.FROM_LOCAL_MAPPER.apply(inputRepo))
        .isEqualTo(expectedRepo);
  }
}
