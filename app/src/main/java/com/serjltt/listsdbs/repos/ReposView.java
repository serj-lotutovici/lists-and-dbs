package com.serjltt.listsdbs.repos;

import com.serjltt.listsdbs.mvi.MviView;
import io.reactivex.Observable;

interface ReposView extends MviView<ReposUiModel> {
  Observable<ReposUiModel> state();
}
