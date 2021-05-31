package com.saikalyandaroju.daggerhilt.source.Network;



import com.saikalyandaroju.daggerhilt.source.Network.models.Blog;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BlogApiSource {

    @GET("blogs")
    Observable<List<Blog>> getBlogs();
}
