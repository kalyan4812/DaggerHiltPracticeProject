package com.saikalyandaroju.daggerhilt.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertBlog(BlogCacheEntity blogCacheEntity);

    @Query("SELECT *  FROM blogs")
    Flowable<List<BlogCacheEntity>> getBlogs();

    @Query("DELETE  FROM blogs")
    Single<Integer> deleteAllBlogs();
}
