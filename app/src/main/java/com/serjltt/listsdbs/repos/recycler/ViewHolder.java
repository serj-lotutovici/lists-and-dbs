package com.serjltt.listsdbs.repos.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import com.serjltt.listsdbs.R;

abstract class ViewHolder extends RecyclerView.ViewHolder {
  static final int TYPE_REPO = 1;
  static final int TYPE_LOADING = 0;

  ViewHolder(View itemView) {
    super(itemView);
  }

  static final class Repo extends ViewHolder {
    @BindView(R.id.repo_owner_icon) ImageView ownerIconView;
    @BindView(R.id.repo_name) TextView nameView;
    @BindView(R.id.repo_description) TextView descriptionView;

    public Repo(View itemView) {
      super(itemView);
      new ViewHolder$Repo_ViewBinding(this, itemView);
    }
  }

  static final class Loading extends ViewHolder {
    @BindView(R.id.progress_view) ProgressBar progressView;

    Loading(View itemView) {
      super(itemView);
      new ViewHolder$Loading_ViewBinding(this, itemView);
    }
  }
}
