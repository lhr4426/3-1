package com.example.mc_week7_homework.ui.logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LogoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("성공적으로 로그아웃 하였습니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }


}
