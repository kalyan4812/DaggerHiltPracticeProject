package com.saikalyandaroju.daggerhilt.di.modules;

import com.saikalyandaroju.daggerhilt.Repository.MainRepository;
import com.saikalyandaroju.daggerhilt.source.Network.BlogApiSource;
import com.saikalyandaroju.daggerhilt.source.local.BlogDao;
import com.saikalyandaroju.daggerhilt.source.local.BlogDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Singleton
    @Provides
    MainRepository provideMainRepository(BlogApiSource blogApiSource, BlogDao blogDao) {
        return new MainRepository(blogDao, blogApiSource);
    }

}
