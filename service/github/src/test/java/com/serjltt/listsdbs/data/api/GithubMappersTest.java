package com.serjltt.listsdbs.data.api;

import com.serjltt.listsdbs.data.api.model.GithubRepository;
import com.serjltt.listsdbs.data.api.model.GithubUser;
import com.serjltt.listsdbs.data.model.Repository;
import com.serjltt.listsdbs.data.model.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is a little bit overboard, but in real life cases the api -> local model conversion is far
 * more complicated. This just prevents us from making any changes to the mappers without checking
 * for consequences.
 */
public final class GithubMappersTest {
  @Test public void mapsApiModelToCommonModel() throws Exception {
    final String login = "C'Tuh";
    final String avatar = "localhost:8080";
    final String repoName = "local-repo";
    final String description = "This is a test";

    GithubUser inputUser = GithubUser.create(login, avatar);
    GithubRepository inputRepo = GithubRepository.create(repoName, description, inputUser);

    User expectedUser = User.create(login, avatar);
    Repository expectedRepo = Repository.create(repoName, description, expectedUser);

    assertThat(GithubMappers.REPOSITORY_MAPPER.apply(inputRepo))
        .isEqualTo(expectedRepo);
  }
}
