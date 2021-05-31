package com.saikalyandaroju.daggerhilt.di.modules;

import com.saikalyandaroju.daggerhilt.MyApplication;
import com.saikalyandaroju.daggerhilt.source.Network.BlogApiSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.internal.managers.ApplicationComponentManager;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder().baseUrl("https://open-api.xyz/placeholder/").addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    @Singleton
    @Provides
    BlogApiSource provideBlogNetworkSource(Retrofit.Builder builder) {
        return builder.build().create(BlogApiSource.class);
    }


}
