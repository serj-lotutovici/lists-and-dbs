package com.serjltt.listsdbs.data;

import com.serjltt.listsdbs.BuildConfig;
import com.serjltt.listsdbs.data.api.GithubJsonAdapterFactory;
import com.serjltt.listsdbs.di.AppScope;
import com.serjltt.listsdbs.rx.RxModule;
import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import javax.inject.Named;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/** Network is generic to the application. So we can safely provide it via a application scope. */
@AppScope @Module
public final class NetworkModule {
  private final HttpUrl apiEndpoint;

  public NetworkModule(HttpUrl apiEndpoint) {
    this.apiEndpoint = apiEndpoint;
  }

  @AppScope @Provides Retrofit provideRetrofit(OkHttpClient client, Moshi moshi,
      @Named(RxModule.IO_SCHEDULER) Scheduler ioScheduler) {
    return new Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(ioScheduler))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .baseUrl(apiEndpoint)
        .build();
  }

  @AppScope @Provides OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
    return new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build();
  }

  @AppScope @Provides HttpLoggingInterceptor provideLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    if (BuildConfig.DEBUG) {
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    } else {
      interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
    }
    return interceptor;
  }

  @AppScope @Provides Moshi provideMoshi() {
    return new Moshi.Builder()
        .add(GithubJsonAdapterFactory.create())
        .build();
  }
}
