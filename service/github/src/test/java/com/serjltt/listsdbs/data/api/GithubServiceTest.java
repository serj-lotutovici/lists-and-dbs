package com.serjltt.listsdbs.data.api;

import com.serjltt.listsdbs.data.api.model.Repository;
import com.serjltt.listsdbs.data.api.model.User;
import com.squareup.moshi.JsonEncodingException;
import com.squareup.moshi.Moshi;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static org.assertj.core.api.Assertions.assertThat;

public final class GithubServiceTest {
  @Rule public final MockWebServer server = new MockWebServer();

  private GithubService service;

  @Before public void setUp() throws Exception {
    Moshi moshi = new Moshi.Builder() //
        .add(GithubAdapterFactory.create()) //
        .build();

    Retrofit retrofit = new Retrofit.Builder() //
        .baseUrl(server.url("/"))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    service = retrofit.create(GithubService.class);
  }

  @Test public void serviceReturnsSuccessResult() throws Exception {
    server.enqueue(new MockResponse().setBody(mockJson));

    Result<List<Repository>> listResult = service.jakesRepositories(1, 15) //
        .test() //
        .assertNoErrors() //
        .assertValueCount(1) //
        .values() //
        .get(0);

    assertThat(listResult.isError()).isFalse();
    //noinspection ConstantConditions It this is null the test will fail.
    assertThat(listResult.response().isSuccessful()).isTrue();

    //noinspection ConstantConditions Same here.
    List<Repository> body = listResult.response().body();
    //noinspection ConstantConditions And here.
    Repository repository = body.get(0);

    assertThat(repository).isEqualTo( //
        Repository.create( //
            "abs.io", //
            "Simple URL shortener for ActionBarSherlock using node.js and express.", //
            User.create( //
                "JakeWharton", //
                "https://avatars0.githubusercontent.com/u/66577?v=4") //
        ));
  }

  @Test public void serviceReturnsErrorResult() throws Exception {
    server.enqueue(new MockResponse().setBody("][")); //  This will make the parsing fail.

    Result<List<Repository>> listResult = service.jakesRepositories(4, 42) //
        .test() //
        .assertNoErrors() //
        .assertValueCount(1) //
        .values() //
        .get(0);

    assertThat(listResult.isError()).isTrue();
    assertThat(listResult.error()) //
        .isInstanceOf(JsonEncodingException.class) //
        .hasMessage("Unexpected value at path $");
  }

  private static final String mockJson = "[\n"
      + "  {\n"
      + "    \"id\": 3070104,\n"
      + "    \"name\": \"abs.io\",\n"
      + "    \"full_name\": \"JakeWharton/abs.io\",\n"
      + "    \"owner\": {\n"
      + "      \"login\": \"JakeWharton\",\n"
      + "      \"id\": 66577,\n"
      + "      \"avatar_url\": \"https://avatars0.githubusercontent.com/u/66577?v=4\",\n"
      + "      \"gravatar_id\": \"\",\n"
      + "      \"url\": \"https://api.github.com/users/JakeWharton\",\n"
      + "      \"html_url\": \"https://github.com/JakeWharton\",\n"
      + "      \"followers_url\": \"https://api.github.com/users/JakeWharton/followers\",\n"
      + "      \"following_url\": \"https://api.github.com/users/JakeWharton/following{/other_user}\",\n"
      + "      \"gists_url\": \"https://api.github.com/users/JakeWharton/gists{/gist_id}\",\n"
      + "      \"starred_url\": \"https://api.github.com/users/JakeWharton/starred{/owner}{/repo}\",\n"
      + "      \"subscriptions_url\": \"https://api.github.com/users/JakeWharton/subscriptions\",\n"
      + "      \"organizations_url\": \"https://api.github.com/users/JakeWharton/orgs\",\n"
      + "      \"repos_url\": \"https://api.github.com/users/JakeWharton/repos\",\n"
      + "      \"events_url\": \"https://api.github.com/users/JakeWharton/events{/privacy}\",\n"
      + "      \"received_events_url\": \"https://api.github.com/users/JakeWharton/received_events\",\n"
      + "      \"type\": \"User\",\n"
      + "      \"site_admin\": false\n"
      + "    },\n"
      + "    \"private\": false,\n"
      + "    \"html_url\": \"https://github.com/JakeWharton/abs.io\",\n"
      + "    \"description\": \"Simple URL shortener for ActionBarSherlock using node.js and express.\",\n"
      + "    \"fork\": false,\n"
      + "    \"url\": \"https://api.github.com/repos/JakeWharton/abs.io\",\n"
      + "    \"forks_url\": \"https://api.github.com/repos/JakeWharton/abs.io/forks\",\n"
      + "    \"keys_url\": \"https://api.github.com/repos/JakeWharton/abs.io/keys{/key_id}\",\n"
      + "    \"collaborators_url\": \"https://api.github.com/repos/JakeWharton/abs.io/collaborators{/collaborator}\",\n"
      + "    \"created_at\": \"2011-12-29T18:02:34Z\",\n"
      + "    \"updated_at\": \"2017-08-09T14:39:21Z\",\n"
      + "    \"pushed_at\": \"2011-12-29T18:02:44Z\",\n"
      + "    \"git_url\": \"git://github.com/JakeWharton/abs.io.git\",\n"
      + "    \"ssh_url\": \"git@github.com:JakeWharton/abs.io.git\",\n"
      + "    \"clone_url\": \"https://github.com/JakeWharton/abs.io.git\",\n"
      + "    \"svn_url\": \"https://github.com/JakeWharton/abs.io\",\n"
      + "    \"homepage\": \"http://abs.io\",\n"
      + "    \"size\": 108,\n"
      + "    \"stargazers_count\": 6,\n"
      + "    \"watchers_count\": 6,\n"
      + "    \"language\": \"JavaScript\",\n"
      + "    \"has_issues\": true,\n"
      + "    \"has_projects\": true,\n"
      + "    \"has_downloads\": true,\n"
      + "    \"has_wiki\": false,\n"
      + "    \"has_pages\": false,\n"
      + "    \"forks_count\": 1,\n"
      + "    \"mirror_url\": null,\n"
      + "    \"open_issues_count\": 0,\n"
      + "    \"forks\": 1,\n"
      + "    \"open_issues\": 0,\n"
      + "    \"watchers\": 6,\n"
      + "    \"default_branch\": \"master\"\n"
      + "  }"
      + "]";
}