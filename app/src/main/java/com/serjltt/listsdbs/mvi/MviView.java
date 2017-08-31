package com.serjltt.listsdbs.mvi;

/**
 * Base contract for a view in a MVI architecture.
 *
 * @param <M> Type of the main view state model.
 */
public interface MviView<M> {
  void render(M model);
}
