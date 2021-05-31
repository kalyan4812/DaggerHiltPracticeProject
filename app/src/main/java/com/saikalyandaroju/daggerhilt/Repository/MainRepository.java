package com.saikalyandaroju.daggerhilt.Repository;

import android.util.Log;

import com.saikalyandaroju.daggerhilt.Utils.ApiResponse;
import com.saikalyandaroju.daggerhilt.Utils.ResponseListener;
import com.saikalyandaroju.daggerhilt.Utils.ResponseStatus;
import com.saikalyandaroju.daggerhilt.source.Network.BlogApiSource;
import com.saikalyandaroju.daggerhilt.source.Network.models.Blog;
import com.saikalyandaroju.daggerhilt.source.local.BlogCacheEntity;
import com.saikalyandaroju.daggerhilt.source.local.BlogDao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainRepository {
    private BlogApiSource blogApiSource;
    private BlogDao blogDao;
    ArrayList<BlogCacheEntity> blogs = new ArrayList<>();

    public MainRepository(BlogDao blogDao, BlogApiSource blogApiSource) {
        this.blogApiSource = blogApiSource;
        this.blogDao = blogDao;
    }

    private <T> void performRequest(Observable<List<T>> observable, ResponseListener<List<T>> responseListener) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<T>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        responseListener.onStart();
                    }

                    @Override
                    public void onNext(@NonNull List<T> ts) {
                        responseListener.onResponse(new ApiResponse(ResponseStatus.SUCCESS, ts, null));
                        //storeInLocalDb(ts);


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseListener.onResponse(new ApiResponse<>(ResponseStatus.ERROR, null, e));
                    }

                    @Override
                    public void onComplete() {
                        responseListener.onFinish();
                    }
                });

    }

    private <T> void storeInLocalDb(List<T> ts) {
        for (T t : ts) {
            blogDao.insertBlog((BlogCacheEntity) t).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            });
        }
    }

    public void getDataFromLocalDb(ResponseListener<List<Blog>> responseListener) {
        performRequestFromDb(blogDao.getBlogs(), responseListener);
    }

    private Observable<List<BlogCacheEntity>> performRequestFromDb(Flowable<List<BlogCacheEntity>> blogs, ResponseListener<List<Blog>> responseListener) {
        Observable<List<BlogCacheEntity>> blogObservable = blogs.subscribeOn(Schedulers.io()).toObservable();
        blogObservable.subscribe(new Consumer<List<BlogCacheEntity>>() {
            @Override
            public void accept(List<BlogCacheEntity> blogCacheEntities) throws Exception {
                responseListener.onResponse(new ApiResponse(ResponseStatus.SUCCESS, blogCacheEntities, null));
                storeInMemory(blogCacheEntities);

            }
        }).isDisposed();
        return blogObservable;
    }

    private void storeInMemory(List<BlogCacheEntity> blogCacheEntities) {
        blogs.clear();
        blogs.addAll(blogCacheEntities);
    }

    public Observable<ArrayList<BlogCacheEntity>> loadFromMemory(ResponseListener<List<Blog>> responseListener) {
      Observable<ArrayList<BlogCacheEntity>> listObservable=  Observable.fromArray(blogs).subscribeOn(Schedulers.io());
      listObservable.subscribe(new Observer<List<BlogCacheEntity>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<BlogCacheEntity> blogCacheEntities) {
                responseListener.onResponse(new ApiResponse(ResponseStatus.SUCCESS, blogCacheEntities, null));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getDataFromLocalDb(responseListener);
            }

            @Override
            public void onComplete() {

            }
        });
      return listObservable;
    }

    public void getBlogs(ResponseListener<List<Blog>> responseListener) {


        performRequest(blogApiSource.getBlogs(), responseListener);
    }
}
