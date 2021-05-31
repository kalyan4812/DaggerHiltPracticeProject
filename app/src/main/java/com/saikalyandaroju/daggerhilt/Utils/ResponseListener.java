package com.saikalyandaroju.daggerhilt.Utils;

public interface ResponseListener<T> {
    void onStart();

    void onFinish();

    void onResponse(ApiResponse<T> apiResponse);
}
