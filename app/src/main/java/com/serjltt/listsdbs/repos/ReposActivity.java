package com.serjltt.listsdbs.repos;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.serjltt.listsdbs.R;
import com.serjltt.listsdbs.di.Injector;
import com.serjltt.listsdbs.di.InjectorActivity;
import com.serjltt.listsdbs.mvi.MviPresenter;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class ReposActivity extends InjectorActivity implements ReposView {
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.progressView) ProgressBar progressBar;

  @Inject MviPresenter<ReposView> presenter;

  private Disposable disposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repos);
    // This object is a view binding and an unbinder.
    // There is no need for an unbinder in an activity.
    new ReposActivity_ViewBinding(this);

    // Bind to the presenter.
    disposable = presenter.bind(this);
  }

  @Override protected void onDestroy() {
    disposable.dispose();
    super.onDestroy();
  }

  @Override protected void onInject(Injector injector) {
    injector.reposComponent().inject(this);
  }

  @Override public void render(ReposUiModel model) {

  }
}