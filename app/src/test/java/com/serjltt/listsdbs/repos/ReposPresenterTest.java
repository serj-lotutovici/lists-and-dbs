package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.data.model.Repository;
import com.serjltt.listsdbs.data.model.User;
import com.serjltt.listsdbs.repos.usecase.GetReposResult;
import com.serjltt.listsdbs.repos.usecase.GetReposUseCase;
import com.serjltt.listsdbs.rx.ImmediateScheduler;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class ReposPresenterTest {
  @Mock private ReposView reposView;
  @Mock private GetReposUseCase getReposUseCase;

  private final ImmediateScheduler scheduler = new ImmediateScheduler();

  private final PublishSubject<ReposUiModel> viewStateSubject = PublishSubject.create();
  private final SingleSubject<GetReposResult> getReposSubject = SingleSubject.create();

  private ReposPresenter presenter;

  @Before public void setUp() throws Exception {
    when(reposView.state()).thenReturn(viewStateSubject);
    when(getReposUseCase.getRepos(anyInt())).thenReturn(getReposSubject);

    presenter = new ReposPresenter(getReposUseCase, scheduler, scheduler);
  }

  @Test public void presenterPropagatesFirstPageResult() throws Exception {
    presenter.bind(reposView);

    viewStateSubject.onNext(ReposUiModel.init());
    getReposSubject.onSuccess(GetReposResult.success(emptyList()));

    verify(reposView)
        .render(ReposUiModel.success(emptyList(), 1, false));
  }

  @Test public void presenterRetrievesFirstPageIfFromOffline() throws Exception {
    presenter.bind(reposView);

    viewStateSubject.onNext(
        ReposUiModel.success(emptyList(), 3, true)
    );

    getReposSubject.onSuccess(GetReposResult.success(notEmptyList()));

    verify(getReposUseCase).getRepos(1);
    verify(reposView)
        .render(ReposUiModel.success(notEmptyList(), 1, false));
  }

  @Test public void presenterPropagatesError() throws Exception {
    presenter.bind(reposView);

    viewStateSubject.onNext(ReposUiModel.init());

    //noinspection ThrowableNotThrown
    Exception expected = new Exception("test");
    getReposSubject.onSuccess(GetReposResult.error(expected));

    verify(reposView)
        .render(ReposUiModel.error(expected, 1));
  }

  private static List<Repository> emptyList() {
    return Collections.emptyList();
  }

  private static List<Repository> notEmptyList() {
    return Collections.singletonList(
        Repository.create("test", "desc",
            User.create("name", null))
    );
  }
}