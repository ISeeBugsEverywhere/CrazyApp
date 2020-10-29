package com.vuchfi.crazyapp.ui.dcalc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DCalcViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DCalcViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}