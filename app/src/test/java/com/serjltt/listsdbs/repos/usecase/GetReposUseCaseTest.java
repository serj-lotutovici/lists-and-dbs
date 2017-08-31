package com.serjltt.listsdbs.repos.usecase;

import com.serjltt.listsdbs.data.api.GithubService;
import com.serjltt.listsdbs.data.api.model.GithubRepository;
import com.serjltt.listsdbs.data.model.Repository;
import io.reactivex.subjects.SingleSubject;
import java.util.Collections;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class GetReposUseCaseTest {
  @Mock private GithubService githubService;

  private SingleSubject<Result<List<GithubRepository>>> serviceSubject;
  private GetReposUseCase useCase;

  @Before public void setUp() throws Exception {
    serviceSubject = SingleSubject.create();
    when(githubService.jakesRepositories(anyInt(), anyInt()))
        .thenReturn(serviceSubject);

    useCase = new RealGetReposUseCase(githubService);
  }

  @Test public void useCaseHandlesSuccessResult() throws Exception {
    Response<List<GithubRepository>> response =
        Response.success(Collections.<GithubRepository>emptyList());
    Result<List<GithubRepository>> result = Result.response(response);

    serviceSubject.onSuccess(result);

    useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(
            GetReposResult.builder()
                .isError(false)
                .hasOffline(false)
                .body(Collections.<Repository>emptyList())
                .build()
        );
  }

  @Test public void useCaseHandlesErrorResult() throws Exception {
    //noinspection ThrowableNotThrown
    Exception expected = new Exception("Some exception");
    Result<List<GithubRepository>> result = Result.error(expected);

    serviceSubject.onSuccess(result);

    useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(
            GetReposResult.builder()
                .isError(true)
                .hasOffline(false)
                .error(expected)
                .build()
        );
  }

  @Test public void useCaseHandlesFailedResponse() throws Exception {
    Response<List<GithubRepository>> response =
        Response.error(401, ResponseBody.create(MediaType.parse("text"), "Failure!"));
    Result<List<GithubRepository>> result = Result.response(response);

    serviceSubject.onSuccess(result);

    // Exceptions are not serializable and don't implement equals().
    GetReposResult getReposResult = useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .values()
        .get(0);

    assertThat(getReposResult.isError()).isTrue();
    assertThat(getReposResult.hasOffline()).isFalse();
    assertThat(getReposResult.body()).isNull();
    assertThat(getReposResult.error())
        .isInstanceOf(Exception.class)
        .hasMessage("Failure!");
  }

  @Test public void useCaseRecoversFromStreamError() throws Exception {
    //noinspection ThrowableNotThrown
    Exception expected = new Exception("Test exception");

    serviceSubject.onError(expected);

    useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(
            GetReposResult.builder()
                .isError(true)
                .hasOffline(false)
                .error(expected)
                .build()
        );
  }
}