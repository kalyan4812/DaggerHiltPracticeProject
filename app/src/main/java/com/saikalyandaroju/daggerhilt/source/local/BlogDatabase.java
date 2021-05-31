package com.saikalyandaroju.daggerhilt.source.local;

import  androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = BlogCacheEntity.class,version = 2)
@TypeConverters({Convertors.class})
public  abstract class BlogDatabase extends RoomDatabase {
   public abstract  BlogDao getBlogDao();
}
