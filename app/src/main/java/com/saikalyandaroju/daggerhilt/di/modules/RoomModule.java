package com.saikalyandaroju.daggerhilt.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.saikalyandaroju.daggerhilt.source.local.BlogDao;
import com.saikalyandaroju.daggerhilt.source.local.BlogDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomModule {



    @Singleton
    @Provides
    String provideDbName() {
        return "Blog_DB";
    }

    @Singleton
    @Provides
    BlogDatabase provideBlogDatabase(String name, @ApplicationContext  Context context) {
        return Room.databaseBuilder(context, BlogDatabase
                .class, name).fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    BlogDao provideBlogDao(BlogDatabase blogDatabase) {
        return blogDatabase.getBlogDao();
    }
}
