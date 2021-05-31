package com.saikalyandaroju.daggerhilt.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.saikalyandaroju.daggerhilt.R;
import com.saikalyandaroju.daggerhilt.Utils.ApiResponse;
import com.saikalyandaroju.daggerhilt.source.Network.models.Blog;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ProgressDialog progressDialog;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        progressDialog = new ProgressDialog(this);
        mainViewModel.getBlogs();
        mainViewModel.loadingStatus.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressDialog.show();
                } else {
                    progressDialog.dismiss();
                }
            }
        });

        subscribeObservers();
    }

    private void subscribeObservers() {
        mainViewModel.responseMutableLiveData.observe(this, new Observer<ApiResponse<List<Blog>>>() {
            @Override
            public void onChanged(ApiResponse<List<Blog>> listApiResponse) {
                if (listApiResponse != null) {
                    bindBlogDetails(listApiResponse);
                    Log.i("check",listApiResponse.data.toString());
                } else {
                    showErrorStatus(listApiResponse);
                    Log.i("check","error");
                }
            }
        });
    }

    private void showErrorStatus(ApiResponse<List<Blog>> listApiResponse) {
        textView.setText(listApiResponse.errorDescription);
    }

    private void bindBlogDetails(ApiResponse<List<Blog>> listApiResponse) {
        StringBuilder sb = new StringBuilder();
        for (Blog blog : listApiResponse.data) {
            sb.append(blog.getTitle() + "\n");
        }
        textView.setText(sb.toString());
    }

}