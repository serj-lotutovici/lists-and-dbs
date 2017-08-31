package com.serjltt.listsdbs.rx;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;
import java.util.concurrent.TimeUnit;

/** A test scheduler that executes the work immediately. */
public class ImmediateScheduler extends Scheduler {
  private final TestScheduler delegate = new TestScheduler();

  @Override public Worker createWorker() {
    return new TestWorker();
  }

  private class TestWorker extends Scheduler.Worker {
    private final Scheduler.Worker delegateWorker = delegate.createWorker();

    @Override public Disposable schedule(Runnable run, long delay, TimeUnit unit) {
      Disposable disposable = delegateWorker.schedule(run, delay, unit);
      delegate.triggerActions();
      return disposable;
    }

    @Override public void dispose() {
      delegateWorker.dispose();
    }

    @Override public boolean isDisposed() {
      return delegateWorker.isDisposed();
    }
  }
}
