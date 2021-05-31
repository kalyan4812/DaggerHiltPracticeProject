package com.saikalyandaroju.daggerhilt.source.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.saikalyandaroju.daggerhilt.source.Network.models.Blog;

@Entity(tableName = "blogs")
public class BlogCacheEntity {

    @PrimaryKey(autoGenerate = true)
    private int pk;
    private Blog blog;

    public BlogCacheEntity(Blog blog) {
        this.blog = blog;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
