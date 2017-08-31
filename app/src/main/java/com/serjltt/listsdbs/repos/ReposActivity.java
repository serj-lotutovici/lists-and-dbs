package com.serjltt.listsdbs.repos;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import com.serjltt.listsdbs.R;
import com.serjltt.listsdbs.di.Injector;
import com.serjltt.listsdbs.di.InjectorActivity;
import com.serjltt.listsdbs.mvi.MviPresenter;
import com.serjltt.listsdbs.repos.recycler.RepoRecyclerAdapter;
import com.serjltt.listsdbs.rx.Ignore;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import javax.inject.Inject;

public class ReposActivity extends InjectorActivity implements ReposView {
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.progress_view) ProgressBar progressView;

  @Inject MviPresenter<ReposView> presenter;

  private Disposable disposable;

  private RepoRecyclerAdapter adapter;

  private ReposUiModel lastState;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repos);
    // This object is a view binding and an unbinder.
    // There is no need for an unbinder in an activity.
    new ReposActivity_ViewBinding(this);

    // Initialize the recycler view.
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new RepoRecyclerAdapter(this, recyclerView);

    // Initialize state.
    lastState = ReposUiModel.init();

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
    progressView.setVisibility(View.GONE);
    lastState = model;

    if (model.isError()) {
      adapter.setLoaded();
      //noinspection ConstantConditions Protected by is error.
      toast("Error loading list: " + model.error().getMessage());
    } else {
      // If it's the first page set the date, otherwise add.
      if (model.page() == 1) {
        adapter.set(model.data());
      } else {
        adapter.add(model.data());
      }

      if (model.isOffline()) {
        toast("Showing offline data.");
      }
    }
  }

  @Override public Observable<ReposUiModel> state() {
    return adapter.loadMoreStream()
        .map(new Function<Ignore, ReposUiModel>() {
          @Override public ReposUiModel apply(Ignore ignore) throws Exception {
            return lastState;
          }
        });
  }

  private void toast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
