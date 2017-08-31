package com.serjltt.listsdbs.repos.usecase;

import com.serjltt.listsdbs.data.api.GithubService;
import com.serjltt.listsdbs.data.api.model.GithubRepository;
import com.serjltt.listsdbs.data.db.LocalService;
import com.serjltt.listsdbs.data.db.model.LocalRepository;
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

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class GetReposUseCaseTest {
  @Mock private GithubService githubService;
  @Mock private LocalService localService;

  private SingleSubject<Result<List<GithubRepository>>> githubServiceSubject;
  private SingleSubject<List<LocalRepository>> localServiceSubject;

  private GetReposUseCase useCase;

  @Before public void setUp() throws Exception {
    githubServiceSubject = SingleSubject.create();
    when(githubService.jakesRepositories(anyInt(), anyInt()))
        .thenReturn(githubServiceSubject);

    localServiceSubject = SingleSubject.create();
    when(localService.repositories()).thenReturn(localServiceSubject);

    useCase = new RealGetReposUseCase(githubService, localService);
  }

  @Test public void useCaseHandlesSuccessResult() throws Exception {
    Response<List<GithubRepository>> response =
        Response.success(Collections.<GithubRepository>emptyList());
    Result<List<GithubRepository>> result = Result.response(response);

    githubServiceSubject.onSuccess(result);

    useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(GetReposResult.success(Collections.<Repository>emptyList()));
  }

  @Test public void useCaseHandlesErrorResultWithLocalCall() throws Exception {
    //noinspection ThrowableNotThrown
    Exception expected = new Exception("Some exception");
    Result<List<GithubRepository>> result = Result.error(expected);

    githubServiceSubject.onSuccess(result);

    localServiceSubject.onSuccess(Collections.<LocalRepository>emptyList());

    useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(GetReposResult.offline(Collections.<Repository>emptyList()));
  }

  @Test public void useCaseHandlesFailedResponseWithOfflineCall() throws Exception {
    Response<List<GithubRepository>> response =
        Response.error(401, ResponseBody.create(MediaType.parse("text"), "Failure!"));
    Result<List<GithubRepository>> result = Result.response(response);

    githubServiceSubject.onSuccess(result);
    localServiceSubject.onSuccess(Collections.<LocalRepository>emptyList());

    useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(GetReposResult.offline(Collections.<Repository>emptyList()));
  }

  @Test public void useCaseRecoversFromStreamError() throws Exception {
    //noinspection ThrowableNotThrown
    Exception expected = new Exception("Test exception");

    githubServiceSubject.onError(expected);
    localServiceSubject.onError(expected);

    useCase.getRepos(1)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(GetReposResult.error(expected));
  }
}