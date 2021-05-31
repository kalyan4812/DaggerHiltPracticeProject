package com.saikalyandaroju.daggerhilt.ui;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.saikalyandaroju.daggerhilt.Repository.MainRepository;
import com.saikalyandaroju.daggerhilt.Utils.ApiResponse;
import com.saikalyandaroju.daggerhilt.Utils.ResponseListener;
import com.saikalyandaroju.daggerhilt.Utils.SingleLiveEvent;
import com.saikalyandaroju.daggerhilt.source.Network.BlogApiSource;
import com.saikalyandaroju.daggerhilt.source.Network.models.Blog;

import java.util.List;

import javax.inject.Inject;

import dagger.assisted.Assisted;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;

@HiltViewModel
public class MainViewModel extends ViewModel {
    public SingleLiveEvent<Boolean> loadingStatus = new SingleLiveEvent<>();
    public MutableLiveData<ApiResponse<List<Blog>>> responseMutableLiveData = new MutableLiveData<>();

    private final MainRepository mainRepository;

    private final SavedStateHandle savedStateHandle;

    @Inject
    public MainViewModel(MainRepository mainRepository, SavedStateHandle savedStateHandle) {
        this.mainRepository = mainRepository;
        this.savedStateHandle = savedStateHandle;
    }


    public void getBlogs() {

        mainRepository.getBlogs(getListener());

    }

    public ResponseListener<List<Blog>> getListener() {
        return new ResponseListener<List<Blog>>() {
            @Override
            public void onStart() {
                loadingStatus.setValue(true);
            }

            @Override
            public void onFinish() {
                loadingStatus.setValue(false);
            }

            @Override
            public void onResponse(ApiResponse<List<Blog>> apiResponse) {

                loadingStatus.setValue(false);
                if (apiResponse != null && apiResponse.data != null) {


                    responseMutableLiveData.setValue(apiResponse);
                }
            }
        };

    }
}

