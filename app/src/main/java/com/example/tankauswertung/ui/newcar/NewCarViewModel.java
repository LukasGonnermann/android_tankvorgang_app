package com.example.tankauswertung.ui.newcar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewCarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewCarViewModel() {
        mText = new MutableLiveData<>();
        // mText.setValue("This is newcar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}