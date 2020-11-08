package com.example.tankauswertung.ui.forecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForecastViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ForecastViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is forecast fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}