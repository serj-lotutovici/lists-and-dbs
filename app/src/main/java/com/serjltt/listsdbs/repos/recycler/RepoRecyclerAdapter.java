package com.serjltt.listsdbs.repos.recycler;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.serjltt.listsdbs.R;
import com.serjltt.listsdbs.data.model.Repository;
import com.serjltt.listsdbs.rx.Ignore;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import java.util.ArrayList;
import java.util.List;

/**
 * {@linkplain RecyclerView.Adapter} that is aware of list scrolls and requests more data when the
 * list comes to and end.
 *
 * This is probably the ugliest code in the project, because adapter and view are so tied together.
 */
public final class RepoRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
  private static final int VISIBLE_THRESHOLD = 3;

  private final List<Repository> repositories = new ArrayList<>();
  private final LayoutInflater inflater;

  private boolean isLoadMore = true;
  private boolean isLoading = false;

  // Using BehaviorSubject to cache the last result.
  private final BehaviorSubject<Ignore> loadMoreSubject = BehaviorSubject.create();

  public RepoRecyclerAdapter(Activity activity, RecyclerView view) {
    inflater = LayoutInflater.from(activity);

    final LinearLayoutManager layoutManager = (LinearLayoutManager) view.getLayoutManager();
    view.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

        if (isLoadMore && !isLoading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
          repositories.add(null);
          notifyItemInserted(repositories.size() - 1);

          loadMoreSubject.onNext(Ignore.INSTANCE);
          isLoading = true;
        }
      }
    });
    view.setAdapter(this);

    // Trigger load more, to notify that adapter requires data.
    loadMoreSubject.onNext(Ignore.INSTANCE);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == ViewHolder.TYPE_REPO) {
      return new ViewHolder.Repo(inflater.inflate(R.layout.view_repo_item, parent, false));
    } else if (viewType == ViewHolder.TYPE_LOADING) {
      return new ViewHolder.Loading(inflater.inflate(R.layout.view_loading_item, parent, false));
    }

    throw new IllegalArgumentException("Unsupported view type: " + viewType);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    if (holder instanceof ViewHolder.Repo) {
      Repository repository = repositories.get(position);
      ViewHolder.Repo repoViewHolder = (ViewHolder.Repo) holder;
      repoViewHolder.nameView.setText(repository.name());
      repoViewHolder.descriptionView.setText(repository.description());
    } else if (holder instanceof ViewHolder.Loading) {
      ViewHolder.Loading loadingViewHolder = (ViewHolder.Loading) holder;
      loadingViewHolder.progressView.setIndeterminate(true);
    } else {
      throw new IllegalArgumentException("Unsupported view holder: " + holder);
    }
  }

  @Override public int getItemCount() {
    return repositories.size();
  }

  @Override public int getItemViewType(int position) {
    return repositories.get(position) != null ? ViewHolder.TYPE_REPO : ViewHolder.TYPE_LOADING;
  }

  public void set(List<Repository> repositories) {
    setLoaded();

    this.repositories.clear();
    this.repositories.addAll(repositories);
    notifyDataSetChanged();
  }

  public void add(List<Repository> repositories) {
    setLoaded();

    this.repositories.addAll(repositories);
    notifyDataSetChanged();
  }

  public Observable<Ignore> loadMoreStream() {
    return loadMoreSubject;
  }

  public void setLoaded() {
    if (isLoading) {
      int toRemove = repositories.size() - 1;
      repositories.remove(toRemove);
      notifyItemRemoved(toRemove);
    }

    isLoading = false;
  }
}
